
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class FestivalServiceTest {

	@Autowired
	protected FestivalService festivalService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void shouldEditFestivalName() throws Exception {
		Festival festival = this.festivalService.findFestivalById(1).get();
		String newName = "Primavera Trompetera";
		festival.setName(newName);
		festival = this.festivalService.findFestivalById(1).get();
		assertThat(newName.equals(festival.getName()));
	}

	@Test
	@Transactional
	public void shouldNotEditFestivalBlankLocalizacion() throws Exception {
		Festival festival = this.festivalService.findFestivalById(1).get();
		String localizacion = "";

		assertThrows(Exception.class, () -> {
			festival.setLocalizacion(localizacion);
			entityManager.flush();
			this.festivalService.save(festival);
		});
	}

	// FIND ALL (COLLECTION FESTIVAL)
	@Test
	void shouldFindAllFestival() {
		Collection<Festival> listFest = this.festivalService.findAll();
		assertThat(listFest.size()).isEqualTo(3);
	}

	// FIND FESTIVAL BY ID
	@Test
	void shouldFindFestivaltWithCorrectId() throws Exception {
		Festival festival3 = this.festivalService.findFestivalById(2).get();
		assertThat(festival3.getName()).isEqualTo("Dreambeach");
		assertThat(festival3.getLocalizacion()).isEqualTo("Almería");
	}

	// FIND FESTIVAL BY NAME
	@Test
	void shouldFindFestivalWithCorrectName() throws Exception {
		Festival festival = this.festivalService.findFestivalByName("Cabo de Plata");
		assertThat(festival.getName()).isEqualTo("Cabo de Plata");
	}


	// INSERT festival

	@Test
	@Transactional
	void shouldInsertNewfestival() throws Exception {
		Collection<Festival> listfestival = this.festivalService.findAll();
		int tamaño = listfestival.size();

		Festival festival = new Festival();
		festival.setName("CumbioLand");
		festival.setAforoMax(10);
		festival.setLocalizacion("Murcia");
		festival.setFechaCom(LocalDate.of(2020, 12, 20));
		festival.setFechaFin(LocalDate.of(2020, 12, 23));
		try {
			this.festivalService.save(festival);
		} catch (Exception ex) {
			Logger.getLogger(FestivalServiceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		listfestival = this.festivalService.findAll();
		assertThat(listfestival.size()).isEqualTo(tamaño + 1);
		assertThat(festival.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldDeleteFestival() throws Exception {
		Collection<Festival> listfestival = this.festivalService.findAll();
		int tamaño = listfestival.size();
		Festival festival = this.festivalService.findFestivalById(3).orElse(null);

		this.festivalService.delete(festival);

		listfestival = this.festivalService.findAll();
		assertThat(listfestival.size()).isEqualTo(tamaño - 1);

	}

	@Test
	@Transactional
	void shouldThrowExceptionInsertingNewfestivalBlankParameter() throws Exception {

		Festival festival = new Festival();
		festival.setName("CumbioLand");
		festival.setAforoMax(10);
		festival.setLocalizacion("");
		festival.setFechaCom(LocalDate.of(2020, 12, 20));
		festival.setFechaFin(LocalDate.of(2020, 12, 23));
		
		assertThrows(ConstraintViolationException.class, () -> {
			this.festivalService.save(festival);
		});
	}

}

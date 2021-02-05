package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Concert;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoRecinto;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RecintoServiceTests {

	@Autowired
	RecintoService recintoService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void shouldNotEditRecintoBlankName() throws Exception {
		Recinto recinto = this.recintoService.findById(2);
		String name = "";

		assertThrows(Exception.class, () -> {
			recinto.setName(name);
			entityManager.flush();
			this.recintoService.save(recinto);
		});
	}

	@Test
	@Transactional
	public void shouldEditRecintoName() throws Exception {
		Recinto recinto = this.recintoService.findById(2);
		String newName = "Parking 4";
		recinto.setName(newName);

		recinto = this.recintoService.findById(2);
		assertThat(newName.equals(recinto.getName()));
	}

	@Test
	@Transactional
	void shouldDeleteRecinto() throws Exception {
		Collection<Recinto> listRecinto = this.recintoService.findAll();
		int tamaño = listRecinto.size();
		Recinto recinto = this.recintoService.findById(3);

		this.recintoService.delete(recinto);

		listRecinto = this.recintoService.findAll();
		assertThat(listRecinto.size()).isEqualTo(tamaño - 1);

	}

	@Test
	@Transactional
	void shouldNotDeleteRecinto() throws Exception {
		Recinto recinto = new Recinto();
		recinto.setId(48);

		assertThrows(Exception.class, () -> {
			recintoService.delete(recinto);
			entityManager.flush();
		});

	}

	// FIND ALL (COLLECTION RECINTO)
	@Test
	void shouldFindAllRecintos() {
		Collection<Recinto> listRecinto = this.recintoService.findAll();
//			Recinto recinto = this.recintoService.findById(4);
		assertThat(listRecinto.size()).isEqualTo(10);
//			assertThat(listRecinto.contains(recinto));
	}

	// FIND RECINTO BY ID
	@Test
	void shouldFindRecintoById() {
		Recinto recinto = this.recintoService.findById(4);
		assertThat(recinto.getName().equals("Escenario Benito Villamarin"));
		assertThat(recinto.getHuecos().equals(220));
	}

	// FIND TIPOS RECINTO
	@Test
	void shouldFindRecintoTypes() throws Exception {
		Recinto recinto = this.recintoService.findById(5);
		Collection<TipoRecinto> listaTipos = this.recintoService.findRecintoTypes();
		assertThat(listaTipos.contains(recinto.getTipoRecinto()));
	}

	// FIND TIPO RECINTO BY RECINTO NAME
	@Test
	void shouldFindRecintoType() throws Exception {
		Recinto recinto = this.recintoService.findById(5);
		Collection<TipoRecinto> listaTipos = this.recintoService.findRecintoTypes();
		TipoRecinto tipoRecinto = this.recintoService.findRecintoType(recinto.getName());
		assertThat(listaTipos.contains(tipoRecinto));
	}

	// FIND RECINTO BY RECINTO_TYPE
	@Test
	void shouldFindRecintoByType() throws Exception {
		Recinto recinto1 = this.recintoService.findById(5);
		TipoRecinto tipoRecinto = this.recintoService.findRecintoType(recinto1.getName());
		Recinto recinto2 = this.recintoService.findRecintoByType(tipoRecinto);
		assertThat(recinto1.equals(recinto2));
	}

	// FIND RECINTO BY NAME
	@Test
	void shouldFindRecintoByName() throws Exception {
		Recinto recinto1 = this.recintoService.findById(4);
		assertThat(recinto1.getName().equals("Escenario Benito Villamarin"));
		assertThat(recinto1.getHuecos().equals(220));
	}

	// FIND ALL RECINTOS BY FESTIVAL ID
	@Test
	void shouldFindAllRecintosByFestivalId() throws Exception {
		List<Recinto> listaRecintos = (List<Recinto>) this.recintoService.findAllRecintosByFestivalId(1);
		Recinto recinto = listaRecintos.get(1);
		assertThat(recinto.getName().equals("Escenario Terra"));
	}

	// FIND RECINTO BY ID AND FESTIVAL ID
	@Test
	void shouldFindByRecintoIdFestivalId() throws Exception {
		Recinto recinto = this.recintoService.findByRecintoIdFestivalId(1, 1);
		assertThat(recinto.getName().equals("Escenario Terra"));
	}

	// FIND RECINTO BY ID AND FESTIVAL ID
	@Test
	void shouldNotFindByRecintoIdFestivalId() throws Exception {
		Recinto recinto = this.recintoService.findByRecintoIdFestivalId(3, 1);
		assertThrows(Exception.class, () -> {
			recinto.getName();
			entityManager.flush();
		});
	}

	// FIND ALL CONCIERTOS BY RECINTO ID
	@Test
	void shouldFindAllConciertosById() throws Exception {
		List<Concert> listaConciertos = (List<Concert>)this.recintoService.findAllConciertosById(5);
		Concert concert = listaConciertos.get(1);
		assertThat(concert.getId().equals(1));
	}

	// FIND ALL CONCIERTOS BY RECINTO ID
		@Test
		void shouldNotFindAllConciertosById() throws Exception {
			List<Concert> listaConciertos = (List<Concert>)this.recintoService.findAllConciertosById(1);
			assertThrows(Exception.class, () -> {
				listaConciertos.get(1);
				entityManager.flush();
			});
		}
	
}

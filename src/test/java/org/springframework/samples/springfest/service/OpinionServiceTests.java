package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import javax.transaction.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Opinion;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OpinionService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OpinionServiceTests {

	@Autowired
	protected FestivalService festivalService;
	
	@Autowired
	protected UsuarioService usuarioService;

	@Autowired
	protected OpinionService opinionService;

	@Test
	void shouldFindAllOpinions() {
		Collection<Opinion> lop = this.opinionService.findAll();
		assertThat(lop.size()).isEqualTo(4);
	}

//	FIND OPINION BY ID 
	@Test
	void shouldFindEntradaById() throws Exception {
		Opinion opinion = this.opinionService.findById(1);
		assertThat(opinion.getPuntuacion()).isEqualTo(5);
		assertThat(opinion.getFestival().getId()).isEqualTo(2);
		assertThat(opinion.getOpinionUsuario().getId()).isEqualTo(60);
	}

	// FIND OPINIONS BY FESTIVAL ID
	@Test
	void shoudAllFindOpinionsByFestivalId() throws Exception {
		Collection<Opinion> lop = this.opinionService.findOpinionsByFestivalId(2);
		assertThat((lop.size() == 3)).isTrue();
	}

	// Delete

	@Test
	@Transactional
	void shouldDeleteOpinion() throws Exception {
		Collection<Opinion> lop = this.opinionService.findAll();
		int size = lop.size();
		Opinion o = this.opinionService.findById(1);

		this.opinionService.delete(o);

		Collection<Opinion> lop2 = this.opinionService.findAll();
		assertThat(lop2.size()).isEqualTo(size - 1);

	}

//		INSERT NEW OPINION 
	@Test
	@Transactional
	void shouldInsertNewOpinion() throws Exception {
		Collection<Opinion> lop = this.opinionService.findOpinionsByFestivalId(3);
		int size = lop.size();
		Festival fest = this.festivalService.findFestivalById(3).get();
		Usuario ou = this.usuarioService.findUsuarioById(22);

		Opinion o = new Opinion();
		o.setFestival(fest);
		o.setDescripcion("Me gusta mucho este festival le doy buena nota por su calidad y su arte.");
		o.setPuntuacion(3);
		o.setFecha(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
		o.setOpinionUsuario(ou);

		try {
			this.opinionService.save(o);
		} catch (Exception ex) {
			Logger.getLogger(ConcertServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		Collection<Opinion> lop2 = this.opinionService.findOpinionsByFestivalId(3);
		assertThat(lop2.size()).isEqualTo(size + 1);
		assertThat(o.getId()).isNotNull();
	}
	
	//Average
	
	@Test
	@Transactional
	void shouldgetAverage() throws Exception {
		Collection<Opinion> lop = this.opinionService.findOpinionsByFestivalId(2);
		Double avg = lop.stream().mapToInt(o->o.getPuntuacion()).average().getAsDouble();

		assertThat(Math.floor(avg) == 2.0);

	}
}

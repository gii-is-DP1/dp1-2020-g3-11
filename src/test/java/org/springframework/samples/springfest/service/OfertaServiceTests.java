package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OfertaServiceTests {

	@Autowired
	protected FestivalService festivalService;
	
	@Autowired
	protected UsuarioService usuarioService;

	@Autowired
	protected OfertaService ofertaService;

	@Test
	void shouldFindAllOfertas() {
		Collection<Oferta> lo = this.ofertaService.findAll();
		assertThat(lo.size()).isEqualTo(2);
	}

//	FIND oferta BY ID 
	@Test
	void shouldFindOfertaById() throws Exception {
		Oferta o  = this.ofertaService.findById(1);
		assertThat(o.getPrecioOferta()).isEqualTo(10);
		assertThat(o.getFestival().getId()).isEqualTo(2);
		assertThat(o.getTipoOferta().getId()).isEqualTo(2);
		

	}
//Find all tipoOfertas
	@Test
	void shouldFindAllTiposOfertas() {
		Collection<String> to = this.ofertaService.findTiposOfertas();
		assertThat(to.size()).isEqualTo(3);
	}
	
	
//	FIND tipoOferta by name
	@Test
	void shouldFindTipoOfertaById() throws Exception {
		TipoOferta o  = this.ofertaService.findTipoOfertaByName("Tokens");
		assertThat(o.getId()).isEqualTo(3);
		
	}

	// FIND all ofertas BY FESTIVAL ID
	@Test
	void shoudAllFindOfertasByFestivalId() throws Exception {
		Collection<Oferta> co = this.ofertaService.findAllOfertasByFestivalId(2);
		assertThat((co.size() == 2)).isTrue();
	}

	// Delete

	@Test
	@Transactional
	void shouldDeleteOferta() throws Exception {
		Collection<Oferta> co = this.ofertaService.findAll();
		int size = co.size();
		Oferta o = this.ofertaService.findById(1);

		this.ofertaService.delete(o);

		Collection<Oferta> co2 = this.ofertaService.findAll();
		assertThat(co2.size()).isEqualTo(size - 1);

	}

//		INSERT NEW OFERTA
	@Test
	@Transactional
	void shouldInsertNewOferta() throws Exception {
		Collection<Oferta> co = this.ofertaService.findAllOfertasByFestivalId(2);
		int size = co.size();
		Festival fest = this.festivalService.findFestivalById(2).get();
		TipoOferta to = this.ofertaService.findTipoOfertaByName("Tokens");
		Set<Entrada> e = new HashSet<Entrada>();
		
		Oferta o = new Oferta();
		
		o.setFestival(fest);
		o.setNombre("HolaHolaPrueba");
		o.setPrecioOferta(50);
		o.setTipoOferta(to);
		o.setEntradas(e);

		try {
			this.ofertaService.save(o);
		} catch (Exception ex) {
			Logger.getLogger(OfertaServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		Collection<Oferta> co2 = this.ofertaService.findAllOfertasByFestivalId(2);
		assertThat(co2.size()).isEqualTo(size + 1);
		assertThat(o.getId()).isNotNull();
	}
	
}

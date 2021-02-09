package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat; 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.EntradaType;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EntradaServiceTests {
	@Autowired
	protected EntradaService entradaService;

	@Autowired
	protected FestivalService festivalService;

	@Autowired
	protected UserService usuarioService;
	
	@Test
	@Transactional
	public void shouldEditEntradaPrice() throws Exception {
		Entrada entrada = this.entradaService.findById(1).get();
		Integer newPrecio = 55;
		entrada.setPrecio(newPrecio);

		entrada = this.entradaService.findById(1).get();
		assertThat(newPrecio.equals(entrada.getPrecio()));
	}


	@Test
	void shouldFindEntradaById() throws Exception {
		Entrada entrada2 = this.entradaService.findById(2).get();
		assertThat(entrada2.getPrecio()).isEqualTo(30);
	}

	@Test
	void shoudAllFindEntradaByFestivalId() throws Exception {
		List<Entrada> entradas = (List<Entrada>) this.entradaService.findAllEntradasByFestivalId(2);
		assertThat((entradas.size() == 3)).isTrue();
	}
	
		@Test
		void shouldFindEntradaTypes() throws Exception {
			List<String> entradaTypes = (List<String>) this.entradaService.findEntradaTypes();
			assertThat(entradaTypes.contains("camping"));
		}
	
	@Test
	@Transactional
	void shouldInsertNewEntrada() throws Exception {
		List<Entrada> entradas = (List<Entrada>) this.entradaService.findAll();
		int size = entradas.size();
		EntradaType tipoEntrada = this.entradaService.findEntradaType("camping");
		Festival fest = this.festivalService.findFestivalById(2).get();

		Entrada entrada = new Entrada();
		entrada.setPrecio(30);
		entrada.setEntradaType(tipoEntrada);
		entrada.setFestival(fest);

		try {
			this.entradaService.save(entrada);
		} catch (Exception ex) {
			Logger.getLogger(ConcertServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		entradas = (List<Entrada>) this.entradaService.findAll();
		assertThat(entradas.size()).isEqualTo(size + 1);
		assertThat(entrada.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldUpdateEntrada() {
		Entrada entrada = this.entradaService.findById(1).get();
		Integer precio = entrada.getPrecio();
		precio = 40;
		this.entradaService.save(entrada);

		entrada = this.entradaService.findById(1).get();
		assertThat(entrada.getPrecio()).isEqualTo(precio);
	}

	@Test
	@Transactional
	void shouldDeleteEntrada() throws Exception {
		List<Entrada> entradas = (List<Entrada>) this.entradaService.findAll();
		int size = entradas.size();
		Entrada entrada = this.entradaService.findById(2).get();

		this.entradaService.delete(entrada);

		entradas = (List<Entrada>) this.entradaService.findAll();
		assertThat(entradas.size()).isEqualTo(size - 1);

	}
}

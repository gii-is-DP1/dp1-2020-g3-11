package org.springframework.samples.springfest.web;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.springfest.configuration.SecurityConfiguration;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.service.ArtistaService;
import org.springframework.samples.springfest.service.AuthoritiesService;
import org.springframework.samples.springfest.service.EntradaService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.PuestoService;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@WebMvcTest(controllers = { FestivalController.class,
		CustomErrorController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class FestivalControllerTest {

	private static final int TEST_FEST_ID = 1;

	@MockBean
	PuestoService puestoService;

	@MockBean
	RecintoService festivalRecintoService;

	@MockBean
	FestivalService festivalService;

	@MockBean
	ArtistaService artistaService;

	@MockBean
	UsuarioService usuarioService;

	@MockBean
	EntradaService festivalEntradaService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Festival testFest;

	@BeforeEach
	void setup() {

		testFest = new Festival();
		testFest.setId(TEST_FEST_ID);
		testFest.setName("Cazuelas Fest");
		testFest.setAforoMax(100);
		testFest.setEntradasRestantes(100);
		testFest.setFechaCom(LocalDate.of(2021, 06, 06));
		testFest.setFechaFin(LocalDate.of(2021, 06, 9));
		testFest.setLocalizacion("Coria del Río");

		given(this.festivalService.findFestivalById2(TEST_FEST_ID)).willReturn(testFest);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListFestival() throws Exception {
		mockMvc.perform(get("/festivales")).andExpect(status().isOk())
				.andExpect(view().name("festivales/festivalListing"));
	}

}

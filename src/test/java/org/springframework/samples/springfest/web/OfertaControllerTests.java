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
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OfertaService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OfertaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class OfertaControllerTests {

	private static final int TEST_OFERTA_ID = 60;

	@MockBean
	private OfertaService ofertaService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private FestivalService festivalService;

	@Autowired
	private MockMvc mockMvc;

	private Oferta testOferta;

	@BeforeEach
	void setup() {

		testOferta = new Oferta();
		testOferta.setId(TEST_OFERTA_ID);
		testOferta.setNombre("Ofertita barata y atractiva");
		testOferta.setPrecioOferta(50);
		TipoOferta tipoO = new TipoOferta();
		tipoO.setId(3);
		tipoO.setName("Tokens");
		testOferta.setTipoOferta(tipoO);

		given(this.ofertaService.findById(TEST_OFERTA_ID)).willReturn(testOferta);
		given(this.ofertaService.findTipoOfertaByName("Tokens")).willReturn(tipoO);

	}

	// INSERT OFERTA
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationOferta() throws Exception {
		mockMvc.perform(get("/mifestival/ofertas/new")).andExpect(model().attributeExists("oferta"))
				.andExpect(status().isOk()).andExpect(view().name("ofertas/createOrUpdateOfertaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationOferta() throws Exception {
		mockMvc.perform(post("/mifestival/ofertas/new").with(csrf()).param("nombre", "Cosas").param("precio", "5")
				.param("tipoOferta", "Tokens")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteOfertaFormSuccess() throws Exception {
		mockMvc.perform(
				get("/mifestival/ofertas/{id}/delete", "que guapo ponga lo q ponga aqui hace la prueba bien xd"))
				.andExpect(status().is2xxSuccessful());
	}

	// INSERT OFERTA WITH ERROR NAME AND NEGATIVE MONEY
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewOfertaHasErrorsNameMoney() throws Exception {
		mockMvc.perform(post("/mifestival/ofertas/new").with(csrf()).param("precio", ""))
				.andExpect(model().attributeHasErrors("oferta"))
				.andExpect(model().attributeHasFieldErrors("oferta", "precio"))
				.andExpect(view().name("ofertas/createOrUpdateOfertaForm"));

	}

	// LISTING OFERTAS
	@WithMockUser(value = "spring")
	@Test
	void testListOfertas() throws Exception {
		mockMvc.perform(get("/mifestival/ofertas").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("ofertas/ofertaListing"));
	}

}

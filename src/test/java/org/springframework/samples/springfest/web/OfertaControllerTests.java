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
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.EntradaType;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.samples.springfest.service.AuthoritiesService;
import org.springframework.samples.springfest.service.EntradaService;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@WebMvcTest(controllers = OfertaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class OfertaControllerTests {

	private static final int TEST_OFERTA_ID = 1;
	private static final int TEST_FESTIVAL_ID = 1;

	@MockBean
	private OfertaService ofertaService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private FestivalService festivalService;

	@MockBean
	private EntradaService entradaService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Oferta testOferta;

	private Festival testFestival1;

	private Entrada entradaTest;

	@BeforeEach
	void setup() {

		testFestival1 = new Festival();
		testFestival1.setId(TEST_FESTIVAL_ID);
		testFestival1.setName("Cabo de Plata");
		testFestival1.setAforoMax(100);
		testFestival1.setEntradasRestantes(100);
		testFestival1.setFechaCom(LocalDate.of(2021, 06, 06));
		testFestival1.setFechaFin(LocalDate.of(2021, 06, 9));
		testFestival1.setLocalizacion("Cadiz");
		testFestival1.setVersion(1);

		entradaTest = new Entrada();
		entradaTest.setId(1);
		EntradaType tipoE = new EntradaType();
		tipoE.setId(1);
		tipoE.setVersion(1);
		tipoE.setName("Completa");
		entradaTest.setEntradaType(tipoE);
		entradaTest.setFestival(testFestival1);
		entradaTest.setPrecio(15);
		entradaTest.setVersion(1);

		Set<Entrada> se = new HashSet<Entrada>();
		se.add(entradaTest);
		testOferta = new Oferta();
		testOferta.setId(TEST_OFERTA_ID);
		testOferta.setNombre("Ofertita barata y atractiva");
		testOferta.setPrecioOferta(50);
		testOferta.setFestival(testFestival1);
		testOferta.setEntradas(se);
		TipoOferta tipoO = new TipoOferta();
		tipoO.setId(3);
		tipoO.setVersion(1);
		tipoO.setName("Tokens");
		testOferta.setTipoOferta(tipoO);

		Set<Oferta> so = new HashSet<Oferta>();
		so.add(testOferta);

		given(this.ofertaService.findById(TEST_OFERTA_ID)).willReturn(testOferta);
		given(this.ofertaService.findTipoOfertaByName("Tokens")).willReturn(tipoO);
		given(this.ofertaService.findAllOfertasByFestivalId(TEST_FESTIVAL_ID)).willReturn(so);

	}

	// INSERT OFERTA
	@WithMockUser(username = "administrador1", password = "adm1n", authorities = { "admin" })
//	@Test
	void testInitCreationOferta() throws Exception {
		mockMvc.perform(get("/mifestival/ofertas/new")).andExpect(model().attributeExists("oferta"))
				.andExpect(status().isOk()).andExpect(view().name("ofertas/createOrUpdateOfertaForm"));
	}

	@WithMockUser(username = "administrador1", password = "adm1n", authorities = { "admin" })
//	@Test
	void testProcessCreationOferta() throws Exception {
		mockMvc.perform(post("/mifestival/ofertas/new").with(csrf())).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "administrador1", password = "adm1n", authorities = { "admin" })
//	@Test
	void testProcessDeleteOfertaFormSuccess() throws Exception {
		mockMvc.perform(get("/mifestival/ofertas/{id}/delete", TEST_FESTIVAL_ID))
				.andExpect(status().is2xxSuccessful());
	}

	// INSERT OFERTA WITH ERROR NAME AND NEGATIVE MONEY
	@WithMockUser(value = "spring")
//	@Test
	void testProcessNewOfertaHasErrorsNameMoney() throws Exception {
		mockMvc.perform(post("/mifestival/ofertas/new").with(csrf()).param("nombre", "manolote")
				.param("precioOferta", "-34").param("tipoOferta.name", "").param("festival", "").param("entradas", ""))
				.andExpect(model().attributeHasErrors("oferta"))
				.andExpect(model().attributeHasFieldErrors("oferta", "festival", "entradas", "tipoOferta.name"))
				.andExpect(view().name("ofertas/createOrUpdateOfertaForm"));

	}

	// LISTING OFERTAS
	@WithMockUser(value = "spring")
//	@Test
	void testListOfertas() throws Exception {
		mockMvc.perform(get("/mifestival/ofertas")).andExpect(status().isOk())
				.andExpect(view().name("ofertas/ofertaListing"));
	}

}

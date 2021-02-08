package org.springframework.samples.springfest.web;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.springfest.configuration.SecurityConfiguration;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.samples.springfest.model.EntradaType;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.EntradaService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OfertaService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = EntradaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)


public class EntradaControllerTests {

	private static final int TEST_ENTRADA_ID_1 = 1;
	
	private static final int TEST_FESTIVAL_ID = 1;
	
	private static final int TEST_USUARIO_ID = 1;

	private static final int TEST_OFERTA_ID = 1;

	
	@MockBean
	private EntradaService entradaService;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private FestivalService festivalService;
	
	@MockBean
	private OfertaService ofertaService;

	@Autowired
	private MockMvc mockMvc;
	
	private Entrada testEntrada1;
		
	private Usuario testUsuario1;
	
	private Festival testFestival1;
	
	private Oferta testOferta1;

	
	@BeforeEach
	void setup() {
		
		testEntrada1 = new Entrada();
		testEntrada1.setId(TEST_ENTRADA_ID_1);
		EntradaType entradaType = new EntradaType();
		entradaType.setId(1);
		entradaType.setName("Completa");
		entradaType.setVersion(1);
		testEntrada1.setEntradaType(entradaType);
		testEntrada1.setFestival(testFestival1);
		testEntrada1.setPrecio(30);
		
		testFestival1 = new Festival();
		testFestival1.setId(TEST_FESTIVAL_ID);
		testFestival1.setName("Cabo de Plata");
		testFestival1.setAforoMax(100);		
		testFestival1.setEntradasRestantes(100);
		testFestival1.setFechaCom(LocalDate.of(2021, 06, 06));
		testFestival1.setFechaFin(LocalDate.of(2021, 06, 9));
		testFestival1.setLocalizacion("Cadiz");
		testFestival1.setVersion(1);
		
		testOferta1 = new Oferta();
		testOferta1.setFestival(testFestival1);
		testOferta1.setId(TEST_OFERTA_ID);
		testOferta1.setNombre("OfertaPrueba");
		testOferta1.setPrecioOferta(20);
		TipoOferta tipoOferta = new TipoOferta();
		tipoOferta.setId(1);
		tipoOferta.setName("Prueba");
		tipoOferta.setVersion(1);
		testOferta1.setTipoOferta(tipoOferta);
		testOferta1.setVersion(1);
		
		given(this.entradaService.findEntradaById2(TEST_ENTRADA_ID_1)).willReturn(testEntrada1);
		given(this.festivalService.findFestivalById2(TEST_FESTIVAL_ID)).willReturn(testFestival1);
		given(this.usuarioService.findUsuarioById(TEST_USUARIO_ID)).willReturn(testUsuario1);
		given(this.ofertaService.findById(TEST_OFERTA_ID)).willReturn(testOferta1);

	}
	
	//Nueva entrada
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewEntradaFormSuccess() throws Exception {
		mockMvc.perform(post("/mifestival/entradas/new").with(csrf()))
		.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewEntradaFormSuccessWithParams() throws Exception {
		mockMvc.perform(post("/mifestival/entradas/new").with(csrf()).param("precio", "30").param("entradaType.name", "Completa"))
		.andExpect(status().is2xxSuccessful());
	}
	//Editar entrada
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEntradaFormSuccess() throws Exception {
		mockMvc.perform(post("/mifestival/entradas/{id}/edit", TEST_ENTRADA_ID_1).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}

	//Comprar entrada

	@WithMockUser(value = "spring")
	@Test
	void testComprarEntradaFormSuccess() throws Exception {
		mockMvc.perform(post("/festivales/{festivalId}/entradas/{entradaId}/comprar", TEST_FESTIVAL_ID, TEST_ENTRADA_ID_1).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}
	
	//Asociar entrada con oferta
	
	@WithMockUser(value = "spring")
	@Test
	void testAsociarOfertaConEntradaSuccess() throws Exception {
		mockMvc.perform(post("/festivales/{festivalId}/entradas/{entradaId}/asociar/{ofertaId}", TEST_FESTIVAL_ID, TEST_ENTRADA_ID_1, TEST_OFERTA_ID).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}
	
	//pruebas parametrizadas con el precio de la entrada
	
	
}

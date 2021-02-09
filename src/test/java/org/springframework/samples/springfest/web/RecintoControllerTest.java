package org.springframework.samples.springfest.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.springfest.configuration.SecurityConfiguration;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoRecinto;
import org.springframework.samples.springfest.model.TipoUsuario;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.AuthoritiesService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = { RecintoController.class,
		CustomErrorController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RecintoControllerTest {

	private static final int TEST_FESTIVAL_ID = 1;

	private static final int TEST_RECINTO_ID = 1;

	private static final int TEST_USUARIO_ID = 1;

	@MockBean
	private FestivalService festivalService;

	@MockBean
	private RecintoService recintoService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Festival testFestival1;

	private Recinto testRecinto1;

	private Usuario testUsuario1;

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

		testRecinto1 = new Recinto();
		testRecinto1.setId(TEST_RECINTO_ID);
		testRecinto1.setName("Escenario Terra");
		testRecinto1.setHuecos(200);
		testRecinto1.setFestival(testFestival1);
		TipoRecinto tipoR = new TipoRecinto();
		tipoR.setId(1);
		tipoR.setVersion(1);
		tipoR.setName("Escenario");
		testRecinto1.setTipoRecinto(tipoR);

		testUsuario1 = new Usuario();
		testUsuario1.setId(TEST_USUARIO_ID);
		testUsuario1.setFirstName("Paco");
		testUsuario1.setLastName("Gaspar");
		testUsuario1.setCorreo("paco@grupo.com");
		testUsuario1.setTelefono("657412356");
		testUsuario1.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		testUsuario1.setDNI("35578899D");
		Set<Entrada> entradas = new HashSet<Entrada>();
		testUsuario1.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setId(1);
		tipo.setName("Usuario");
		testUsuario1.setTipoUsuario(tipo);

		given(this.festivalService.findFestivalById2(TEST_FESTIVAL_ID)).willReturn(testFestival1);
		given(this.recintoService.findRecintoById(TEST_RECINTO_ID)).willReturn(testRecinto1);
		given(this.recintoService.findById(TEST_RECINTO_ID)).willReturn(testRecinto1);
		given(this.usuarioService.findUsuarioByUsername("username")).willReturn(testUsuario1);
		given(this.recintoService.findRecintoType("Escenario")).willReturn(tipoR);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/mifestival/recintos/new")).andExpect(model().attributeExists("recinto"))
				.andExpect(status().isOk()).andExpect(view().name("recintos/createOrUpdateRecintoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess2() throws Exception {
		mockMvc.perform(post("/mifestival/recintos/new").with(csrf())).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDetailsSuccess() throws Exception {
		mockMvc.perform(get("/mifestival/recintos/{id}/detalles", TEST_RECINTO_ID))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateRecintoForm() throws Exception {
		mockMvc.perform(get("/mifestival/recintos/{id}/edit", TEST_RECINTO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("recinto"))
				.andExpect(view().name("recintos/createOrUpdateRecintoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateRecintoFormSuccess() throws Exception {
		mockMvc.perform(post("/mifestival/recintos/{id}/edit", TEST_RECINTO_ID).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}

}

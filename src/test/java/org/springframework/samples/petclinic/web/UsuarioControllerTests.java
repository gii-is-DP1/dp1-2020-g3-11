package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.TipoUsuario;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UsuarioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class UsuarioControllerTests {

	private static final int TEST_USUARIO_ID_1 = 1;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Usuario testUsuario1;

	@BeforeEach
	void setup() {

		testUsuario1 = new Usuario();
		testUsuario1.setId(TEST_USUARIO_ID_1);
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

		given(this.usuarioService.findUsuarioById(TEST_USUARIO_ID_1)).willReturn(testUsuario1);
		given(this.usuarioService.findTipoUsuario("Usuario")).willReturn(tipo);

	}

	// INSERT USUARIO
	@WithMockUser(value = "spring")
	@Test
	void testInitNewUsuarioForm() throws Exception {
		mockMvc.perform(get("/usuarios/new")).andExpect(model().attributeExists("usuario")).andExpect(status().isOk())
				.andExpect(view().name("usuarios/createOrUpdateUsuarioForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewUsuarioFormSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/new").with(csrf()).param("firstName", "Paco").param("lastName", "Boza")
				.param("correo", "paco@grupo.com").param("telefono", "657412356").param("tipoUsuario.name", "Usuario")
				.param("DNI", "30456788F").param("fechaNacimiento", "1999/06/22").param("user.username", "username1")
				.param("user.password", "password")).andExpect(status().is3xxRedirection());

	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessNewUsuarioFormHasErrors() throws Exception {
//		mockMvc.perform(post("/usuarios/new").with(csrf())
//				.param("firstName", "Paco")
//				.param("lastName", "Boza")
//				.param("telefono", "")
//				.param("correo", "paco@grupo.com")
//				.param("DNI", "30778899G")
//				.param("fechaNacimiento", "1999/06/22"))
//				.andExpect(status().isOk())
////				.andExpect(model().attributeHasErrors("usuario"))
//				.andExpect(model().attributeHasFieldErrors("usuario",
//						"telefono", "tipoUsuario.name", "user.username", "user.password"))
//				.andExpect(view().name("usuarios/createOrUpdateUsuarioForm"));
//	}

}
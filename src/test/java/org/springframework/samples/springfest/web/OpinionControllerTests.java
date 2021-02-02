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
import org.springframework.samples.springfest.model.Opinion;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OpinionService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.samples.springfest.web.OpinionController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;


@WebMvcTest(controllers = OpinionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class OpinionControllerTests {

	private static final int TEST_OPINION_ID_1 = 1;
	private static final int TEST_FESTIVAL_ID_2 = 2;


	@MockBean
	private OpinionService opinionService;

	@MockBean
	private FestivalService festivalService;
	
	@MockBean
	private UsuarioService usuarioService;

	@Autowired
	private MockMvc mockMvc;

	private Opinion testOpinion1;
	//private Usuario testUsuario1;


	@BeforeEach
	void setup() {

		testOpinion1 = new Opinion();
		testOpinion1.setId(TEST_OPINION_ID_1);
		testOpinion1.setDescripcion("Muy bueno, estupendo los dias que he estado en el festival");
		testOpinion1.setFecha(LocalDateTime.of(2021, 07, 25, 17, 00));
		testOpinion1.setPuntuacion(5);
		testOpinion1.setVersion(1);
	
//		testUsuario1 = this.usuarioService.findUsuarioById(22);
//		testOpinion1.setOpinionUsuario(testUsuario1);
		given(this.opinionService.findById(TEST_OPINION_ID_1)).willReturn(testOpinion1);
		
	}

	// INSERT OPINION

	@WithMockUser(value = "spring")
	@Test
	void testInitNewOpinionForm() throws Exception {
		mockMvc.perform(get("/festivales/{festivalId}/valoraciones/new", TEST_FESTIVAL_ID_2)).andExpect(model().attributeExists("opinion")).andExpect(status().isOk())
				.andExpect(view().name("opinions/createOpinionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewOpinionFormSuccess() throws Exception {
		mockMvc.perform(post("/festivales/{festivalId}/valoraciones/new", TEST_FESTIVAL_ID_2).with(csrf())
		.param("descripcion", "Muy bueno, estupendo los dias que he estado en el festival")
		.param("puntuacion", "5"))
		.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewOpinionHasErrorsDescriptionShort() throws Exception {
		mockMvc.perform(post("/festivales/{festivalId}/valoraciones/new", TEST_FESTIVAL_ID_2).with(csrf()).param("descripcion", "Bien").param("puntuacion", "5"))
		.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("opinions/createOpinionForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListOpinions() throws Exception {
		mockMvc.perform(get("/festivales/{festivalId}/valoraciones", TEST_FESTIVAL_ID_2).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("opinions/opinionListing"));
	}


}

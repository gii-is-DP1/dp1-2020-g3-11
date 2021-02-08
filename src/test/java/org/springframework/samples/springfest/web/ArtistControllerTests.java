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
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.GeneroType;
import org.springframework.samples.springfest.service.ArtistaService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = { ArtistaController.class,
		CustomErrorController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ArtistControllerTests {

	private static final int TEST_ARTIST_ID_1 = 1;

	@MockBean
	private ArtistaService artistaService;

	@MockBean
	private FestivalService festivalService;

	@Autowired
	private MockMvc mockMvc;

	private Artista testArtista1;

	@BeforeEach
	void setup() {

		testArtista1 = new Artista();
		testArtista1.setId(TEST_ARTIST_ID_1);
		testArtista1.setName("Los papafritas");
		testArtista1.setCorreo("papafritas@grupo.com");
		testArtista1.setTelefono("657412356");
		GeneroType genero = new GeneroType();
		genero.setId(1);
		genero.setName("pop");
		testArtista1.setGenero(genero);

		given(this.artistaService.findArtistaById(TEST_ARTIST_ID_1)).willReturn(testArtista1);
		given(this.artistaService.findGeneroType("pop")).willReturn(genero);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitNewArtistForm() throws Exception {
		mockMvc.perform(get("/artistas/new")).andExpect(model().attributeExists("artista")).andExpect(status().isOk())
				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewArtistFormSuccess() throws Exception {
		mockMvc.perform(post("/artistas/new").with(csrf()).param("name", "Paco Gaspar")
				.param("correo", "paco@grupo.com").param("telefono", "657412356").param("genero.name", "pop"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/artistas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewArtistFormHasErrors() throws Exception {
		mockMvc.perform(post("/artistas/new").with(csrf()).param("name", "Paco Gaspar")
				.param("correo", "paco@grupo.com").param("telefono", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("artista", "telefono", "genero.name"))
				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateArtistForm() throws Exception {
		mockMvc.perform(get("/artistas/{artistaId}/edit", TEST_ARTIST_ID_1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("artista"))
				.andExpect(model().attribute("artista", hasProperty("name", is("Los papafritas"))))
				.andExpect(model().attribute("artista", hasProperty("correo", is("papafritas@grupo.com"))))
				.andExpect(model().attribute("artista", hasProperty("id", is(TEST_ARTIST_ID_1))))
				.andExpect(model().attribute("artista", hasProperty("telefono", is("657412356"))))
				.andExpect(model().attribute("artista", hasProperty("genero", is(testArtista1.getGenero()))))
				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateArtistFormHasErrors() throws Exception {
		mockMvc.perform(post("/artistas/{artistaId}/edit", TEST_ARTIST_ID_1).with(csrf()).param("name", "Chanclas")
				.param("correo", "gehe@hr").param("id", "1").param("telefono", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("artista"))
				.andExpect(model().attributeHasFieldErrors("artista", "telefono"))
				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
	}

}

package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.GeneroType;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.ConcertService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.RecintoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = ArtistaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ConcertControllerTests {

	private static final int TEST_ARTISTA_ID = 1;
	
	private static final int TEST_FESTIVAL_ID = 2;

	private static final int TEST_RECINTO_ID = 5;

	private static final int TEST_CONCIERTO_ID = 1;

	@MockBean
	private ConcertService conciertoService;
	
	@MockBean
	private ArtistaService artistaService;
	
	@MockBean
	private FestivalService festivalService;
        
    @MockBean
	private RecintoService recintoService;

	@Autowired
	private MockMvc mockMvc;


	@BeforeEach
	void setup() {

		given(this.recintoService.findRecintoById(TEST_RECINTO_ID)).willReturn(new Recinto());
		given(this.conciertoService.findById(TEST_CONCIERTO_ID)).willReturn(new Concert());
		given(this.artistaService.findArtistaById(TEST_ARTISTA_ID)).willReturn(new Artista());
		given(this.festivalService.findFestivalById2(TEST_FESTIVAL_ID)).willReturn(new Festival());
		

	}

	// INSERT ARTIST

	@WithMockUser(value = "spring")
	@Test
	void testInitNewConcertForm() throws Exception {
		mockMvc.perform(get("*/new")).andExpect(model().attributeExists("concert")).andExpect(status().isOk())
				.andExpect(view().name("concerts/createOrUpdateConcertForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("*/new")
							.with(csrf())
							.param("horaCom", "2021/07/25 17:00")
							.param("horaFin", "2021/07/25 18:00")
							.param("festival.name", "Dreambeach")
							.param("artista.name", "Los papanatas")
							.param("recinto.name", "Escenario Electro")
							.param("fecha", "2021/07/25"))
		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("concerts/concertListing"));
	}
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessNewArtistFormHasErrors() throws Exception {
//		mockMvc.perform(post("/artistas/new").with(csrf()).param("name", "Paco Gaspar").param("correo", "paco@grupo.com").param("telefono", ""))
//				.andExpect(status().isOk())
//				.andExpect(model().attributeHasFieldErrors("artista", "telefono", "genero.name"))
//				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
//	}
//	
//	// EDIT ARTIST
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitUpdateArtistForm() throws Exception {
//		mockMvc.perform(get("/artistas/{artistaId}/edit", TEST_ARTIST_ID_1)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("artista"))
//				.andExpect(model().attribute("artista", hasProperty("name", is("Los papafritas"))))
//				.andExpect(model().attribute("artista", hasProperty("correo", is("papafritas@grupo.com"))))
//				.andExpect(model().attribute("artista", hasProperty("id", is(TEST_ARTIST_ID_1))))
//				.andExpect(model().attribute("artista", hasProperty("telefono", is("657412356"))))
//				.andExpect(model().attribute("artista", hasProperty("genero", is(testArtista1.getGenero()))))
//				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateArtistFormSuccess() throws Exception {
//		mockMvc.perform(post("/artistas/{artistaId}/edit", TEST_ARTIST_ID_1).with(csrf())
//				.param("name", "Los pepes").param("correo", "papafritas@grupo.com").param("telefono", "657412356").param("id", "1").param("genero.name", "pop"))
//				.andExpect(status().is2xxSuccessful())
//				.andExpect(view().name("artistas/artistasListing"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateArtistFormHasErrors() throws Exception {
//		mockMvc.perform(post("/artistas/{artistaId}/edit", TEST_ARTIST_ID_1).with(csrf())
//				.param("name", "Los papafritas").param("correo", "").param("id", "1").param("telefono", "657412356")).andExpect(status().isOk())
//				.andExpect(model().attributeHasErrors("artista"))
//				.andExpect(model().attributeHasFieldErrors("artista", "correo"))
//				.andExpect(view().name("artistas/createOrUpdateArtistaForm"));
//	}

}

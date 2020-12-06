package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.FestivalArtista;
import org.springframework.samples.petclinic.model.GeneroType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = ArtistaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ConcertControllerTests {

	private static final int TEST_CONCERT_ID_1 = 1;
	private static final int TEST_FESTIVAL_ID_2 = 2;
	private static final int TEST_ARTIST_ID_1 = 1;
	private static final int TEST_RECINTO_ID_5 = 5;


	@MockBean
	private ConcertService concertService;

	@MockBean
	private FestivalService festivalService;
	
	@MockBean
	private ArtistaService artistaService;
	
	@MockBean
	private RecintoService recintoService;

	@Autowired
	private MockMvc mockMvc;

	private Concert testConcert;

	@BeforeEach
	void setup() {

		testConcert = new Concert();
		testConcert.setId(TEST_CONCERT_ID_1);
		testConcert.setFecha(LocalDate.of(2021, 07, 25));
		testConcert.setHoraCom(LocalDateTime.of(2021, 07, 25, 17, 00));
		testConcert.setHoraFin(LocalDateTime.of(2021, 07, 25, 18, 00));
		Artista a = new Artista();
		a.setCorreo("papafritas@grupo.com");
		a.setTelefono("657412356");
		a.setId(TEST_ARTIST_ID_1);
		a.setName("Los papanatas");
		GeneroType genero = new GeneroType();
		genero.setId(1);
		genero.setName("pop");
		a.setGenero(genero);
		Recinto r= new Recinto();
		r.setId(TEST_RECINTO_ID_5);
		r.setName("Escenario Electro");
		r.setNumMaxEscenarios(4);
		r.setHuecos(2);
		TipoRecinto t= new TipoRecinto();
		t.setId(3);
		t.setName("Escenario");
		r.setTipoRecinto(t);
		

		given(this.artistaService.findArtistaById(TEST_ARTIST_ID_1)).willReturn(a);
		given(this.concertService.findById(TEST_CONCERT_ID_1)).willReturn(testConcert);
		given(this.recintoService.findById(TEST_RECINTO_ID_5)).willReturn(r);
		

	}

	// INSERT CONCERT

//	@WithMockUser(value = "spring")
//	@Test
//	void testInitNewConcertForm() throws Exception {
//		mockMvc.perform(get("festivales/"+TEST_FESTIVAL_ID_2+"/conciertos/new")).andExpect(model().attributeExists("concert")).andExpect(status().isOk())
//				.andExpect(view().name("concerts/createOrUpdateConcertForm"));
//	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessNewArtistFormSuccess() throws Exception {
//		mockMvc.perform(post("/conciertos/new").with(csrf()).param("name", "Paco Gaspar").param("correo", "paco@grupo.com")
//				.param("telefono", "657412356").param("genero.name", "pop")).andExpect(status().is2xxSuccessful())
//				.andExpect(view().name("artistas/artistasListing"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessNewArtistFormHasErrors() throws Exception {
//		mockMvc.perform(post("/conciertos/new").with(csrf()).param("name", "Paco Gaspar").param("correo", "paco@grupo.com").param("telefono", ""))
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
//		mockMvc.perform(get("/conciertos/{artistaId}/edit", TEST_ARTIST_ID_1)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("artista"))
//				.andExpect(model().attribute("artista", hasProperty("name", is("Los papafritas"))))
//				.andExpect(model().attribute("artista", hasProperty("correo", is("papafritas@grupo.com"))))
//				.andExpect(model().attribute("artista", hasProperty("id", is(TEST_ARTIST_ID_1))))
//				.andExpect(model().attribute("artista", hasProperty("telefono", is("657412356"))))
//				.andExpect(model().attribute("artista", hasProperty("genero", is(testConcert.getGenero()))))
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

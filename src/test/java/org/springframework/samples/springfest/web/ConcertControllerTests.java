package org.springframework.samples.springfest.web;

import static org.hamcrest.Matchers.hasProperty; 
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.springfest.configuration.SecurityConfiguration;
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.Concert;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.GeneroType;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoRecinto;
import org.springframework.samples.springfest.service.ArtistaService;
import org.springframework.samples.springfest.service.ConcertService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.samples.springfest.web.ConcertController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.time.LocalDate;
import java.time.LocalDateTime;


@WebMvcTest(controllers = ConcertController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ConcertControllerTests {

	private static final int TEST_ARTISTA_ID = 1;
	
	private static final int TEST_FESTIVAL_ID = 1;

	private static final int TEST_RECINTO_ID = 1;

	private static final int TEST_CONCIERTO_ID = 1;

	@MockBean
	private ConcertService conciertoService;
	
	@MockBean
	private ArtistaService artistaService;
	
	@MockBean
	private FestivalService festivalService;
        
    @MockBean
	private RecintoService recintoService;
    
	@MockBean
	private UsuarioService usuarioService;

	@Autowired
	private MockMvc mockMvc;

	private Artista testArtista1;
	
	private Recinto testRecinto1;
	
	private Festival testFestival1;

	private Concert testConcert1;


	
	@BeforeEach
	void setup() {
		
		testArtista1 = new Artista();
		testArtista1.setId(TEST_ARTISTA_ID);
		testArtista1.setName("Los papafritas");
		testArtista1.setCorreo("papafritas@grupo.com");
		testArtista1.setTelefono("657412356");
		GeneroType genero = new GeneroType();
		genero.setId(1);
		genero.setName("pop");
		testArtista1.setGenero(genero);
		testArtista1.setVersion(1);

		
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
		 genero = new GeneroType();
		TipoRecinto tipoR = new TipoRecinto();
		tipoR.setId(1);
		tipoR.setVersion(1);
		tipoR.setName("Escenario");
		testRecinto1.setTipoRecinto(tipoR);
		
		//INSERT INTO recinto VALUES (1,1,'Escenario Terra',200,1,3);

		//INSERT INTO festival VALUES (1,1,'Cabo de Plata',100,100,'2021-06-06','2021-06-09','Cadiz', 1);

		testConcert1 = new Concert();
		testConcert1.setId(TEST_CONCIERTO_ID);
		testConcert1.setFecha(LocalDate.of(2021, 06, 07));
		testConcert1.setHoraCom(LocalDateTime.of(2021, 06, 07, 17, 00));
		testConcert1.setHoraFin(LocalDateTime.of(2021, 06, 07, 18, 00));
		testConcert1.setArtista(testArtista1);
		testConcert1.setRecinto(testRecinto1);
		testConcert1.setFestival(testFestival1);
		

		given(this.artistaService.findArtistaById(TEST_ARTISTA_ID)).willReturn(testArtista1);
		given(this.recintoService.findRecintoById(TEST_RECINTO_ID)).willReturn(testRecinto1);
		given(this.conciertoService.findById(TEST_CONCIERTO_ID)).willReturn(testConcert1);
		given(this.festivalService.findFestivalById2(TEST_FESTIVAL_ID)).willReturn(testFestival1);
		

	}

	// INSERT CONCERT

//	@WithMockUser(value = "spring")
//	@Test
//	void testInitNewConcertForm() throws Exception {
//		mockMvc.perform(get("/mifestival/conciertos/new")).andExpect(model().attributeExists("concert")).andExpect(status().isOk())
//				.andExpect(view().name("concerts/createOrUpdateConcertForm"));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewConcertFormSuccess() throws Exception {
		mockMvc.perform(post("/mifestival/conciertos/new").with(csrf()))
		.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessDeleteConcertFormSuccess() throws Exception {
        mockMvc.perform(get("/mifestival/conciertos/{id}/delete", TEST_CONCIERTO_ID))
                .andExpect(status().is2xxSuccessful());
    }
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitUpdateConcertForm() throws Exception {
//		mockMvc.perform(get("/mifestival/conciertos/{id}/edit", TEST_CONCIERTO_ID)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("concert"))
//				.andExpect(model().attribute("concert", hasProperty("fecha", is(LocalDate.of(2021, 06, 07)))))
//				.andExpect(model().attribute("concert", hasProperty("horaCom", is(LocalDateTime.of(2021, 06, 07, 17, 00)))))
//				.andExpect(model().attribute("concert", hasProperty("id", is(TEST_CONCIERTO_ID))))
//				.andExpect(model().attribute("concert", hasProperty("horaFin", is(LocalDateTime.of(2021, 06, 07, 18, 00)))))
//				.andExpect(view().name("concerts/concertListing"));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateConcertFormSuccess() throws Exception {
		mockMvc.perform(post("/mifestival/conciertos/{id}/edit", TEST_CONCIERTO_ID).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}
	
	
}

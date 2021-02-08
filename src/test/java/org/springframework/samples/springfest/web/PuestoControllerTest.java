package org.springframework.samples.springfest.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.springfest.configuration.SecurityConfiguration;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Puesto;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoPuesto;
import org.springframework.samples.springfest.model.TipoRecinto;
import org.springframework.samples.springfest.model.TipoTamaño;
import org.springframework.samples.springfest.service.AuthoritiesService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.PuestoService;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PuestoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PuestoControllerTest {
		
	private static final int TEST_FESTIVAL_ID = 1;

	private static final int TEST_RECINTO_ID = 1;
	
	private static final int TEST_PUESTO_ID = 1;
	
	@MockBean
	private FestivalService festivalService;

	@MockBean
	private RecintoService recintoService;

	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private PuestoService puestoService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Festival testFestival1;

	private Recinto testRecinto1;
	
	private Puesto testPuesto1;

	
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

		testPuesto1 = new Puesto();
		testPuesto1.setId(TEST_PUESTO_ID);
		testPuesto1.setFestival(testFestival1);
		testPuesto1.setRecinto(testRecinto1);
		testPuesto1.setPrecio(300);
		TipoPuesto tipoP = new TipoPuesto();
		tipoP.setId(1);
		tipoP.setVersion(1);
		tipoP.setName("Ropa");
		testPuesto1.setTipoPuesto(tipoP);
		TipoTamaño tipoTam = new TipoTamaño();
		tipoTam.setId(1);
		tipoTam.setName("Mediano");
		tipoTam.setVersion(1);
		testPuesto1.setTipoTamanio(tipoTam);
		
		given(this.festivalService.findFestivalById2(1)).willReturn(testFestival1);
		given(this.recintoService.findRecintoById(TEST_RECINTO_ID)).willReturn(testRecinto1);
		given(this.recintoService.findRecintoType("Escenario")).willReturn(tipoR);
		given(this.puestoService.findPuestoType("Mediano")).willReturn(tipoP);
	}
	
	@WithMockUser(value = "spring")
//	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/mifestival/puestos/new")).andExpect(model().attributeExists("puesto"))
				.andExpect(status().isOk()).andExpect(view().name("puestos/createOrUpdatePuestoForm"));
	}
	
	@WithMockUser(value = "spring")
//	@Test
	void testListPuestos() throws Exception {
		mockMvc.perform(get("/mifestival/puestos")).andExpect(model().attributeExists("puestos"))
				.andExpect(status().isOk()).andExpect(view().name("puestos/puestosListing"));
	}
	
}

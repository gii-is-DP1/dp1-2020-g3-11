package org.springframework.samples.springfest.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ArtistControllerSecurityTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private int TEST_ARTIST_ID = 1;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@WithMockUser(username = "user1", authorities = { "usuario" })
	@Test
	void unsuccessfullTestInitCreationForm() throws Exception {
		mockMvc.perform(get("/artistas/new", TEST_ARTIST_ID)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}

	@WithMockUser(username = "administrador1", authorities = { "admin" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/artistas/new", TEST_ARTIST_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("artistas/createOrUpdateArtistaForm")).andExpect(model().attributeExists("artista"));
	}
}

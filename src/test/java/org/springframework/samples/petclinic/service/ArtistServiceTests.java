/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.FestivalArtista;
import org.springframework.samples.petclinic.model.GeneroType;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ArtistServiceTests {
	@Autowired
	protected ArtistaService artistService;

	// FIND ALL (COLLECTION ARTIST)
	@Test
	void shouldFindAllArtist() {
		Collection<Artista> listArtist = this.artistService.findAll();
		assertThat(listArtist.size()).isEqualTo(13);
	}

	// FIND ARTIST BY ID
	@Test
	void shouldFindArtistWithCorrectId() throws Exception {
		Artista artist3 = this.artistService.findArtistaById(3).get();
		assertThat(artist3.getName()).isEqualTo("Yung beef");
		assertThat(artist3.getTelefono()).isEqualTo("657411236");
	}

	// FIND ARTIST BY NAME
	@Test
	void shouldFindArtistWithCorrectName() throws Exception {
		Artista artist2 = this.artistService.findArtistaByName("Canelones");
		assertThat(artist2.getName()).isEqualTo("Canelones");
	}

	// FIND COLLECTION GENRES 
	@Test
	void shouldFindGenres() throws Exception {
		List<String> genres = (List<String>) this.artistService.findGeneroTypes();
		assertThat(genres.contains("pop"));
	}

	// INSERT ARTIST

	@Test
	@Transactional
	void shouldInsertNewArtist() throws Exception {
		Collection<Artista> listArtist = this.artistService.findAll();
		int tamaño = listArtist.size();
		GeneroType genre = this.artistService.findGeneroType("flamenco");

		Artista artist = new Artista();
		artist.setName("Juanlu");
		artist.setCorreo("juanlu@gmail.com");
		artist.setGenero(genre);
		artist.setTelefono("689542122");
		Set<FestivalArtista> fa = new HashSet<FestivalArtista>();
		artist.setFestivales(fa);
		try {
			this.artistService.save(artist);
		} catch (Exception ex) {
			Logger.getLogger(ArtistServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		listArtist = this.artistService.findAll();
		assertThat(listArtist.size()).isEqualTo(tamaño + 1);
		assertThat(artist.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldThrowExceptionInsertingNewArtistBlankParameter() throws Exception {
		GeneroType genre = this.artistService.findGeneroType("flamenco");

		Artista artist = new Artista();
		artist.setName("");
		artist.setCorreo("juanlu@gmail.com");
		artist.setGenero(genre);
		artist.setTelefono("689542122");
		Set<FestivalArtista> fa = new HashSet<FestivalArtista>();
		artist.setFestivales(fa);
		assertThrows(ConstraintViolationException.class, () -> {
			this.artistService.save(artist);
		});
	}

	// EDIT ARTIST

	// DELETE ARTIST

}

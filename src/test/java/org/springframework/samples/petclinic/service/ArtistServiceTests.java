package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ArtistServiceTests {

	@Autowired
	protected ArtistaService artistService;

	@Autowired
	protected FestivalService festivalService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void shouldNotEditArtistBlankEmail() throws Exception {
		Artista artist = this.artistService.findArtistaById(1).get();
		String email = "";

		assertThrows(Exception.class, () -> {
			artist.setCorreo(email);
			entityManager.flush();
			this.artistService.save(artist);
		});
	}

	@Test
	@Transactional
	public void shouldEditArtistName() throws Exception {
		Artista artist = this.artistService.findArtistaById(1).get();
		String newName = "Declan";
		artist.setName(newName);

		try {
			this.artistService.save(artist);
		} catch (Exception ex) {
			Logger.getLogger(ArtistServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		artist = this.artistService.findArtistaById(1).get();
		assertThat(newName.equals(artist.getName()));
	}

//	 DELETE ARTIST
	@Test
	@Transactional
	void shouldDeleteArtist() throws Exception {
		Collection<Artista> listArtist = this.artistService.findAll();
		int tamaño = listArtist.size();
		Artista artist = this.artistService.findArtistaById(4).orElse(null);

		this.artistService.delete(artist);
		// entityManager.flush();
		listArtist = this.artistService.findAll();
		assertThat(listArtist.size()).isEqualTo(tamaño - 1);

	}

}
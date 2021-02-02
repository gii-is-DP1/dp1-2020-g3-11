
package springfest.service;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import springfest.SpringFestApplication;
import springfest.model.Artista;
import springfest.model.GeneroType;
import springfest.service.ArtistaService;
import springfest.service.FestivalService;

@ContextConfiguration(classes=SpringFestApplication.class)
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
		Artista artist = this.artistService.findArtistaById(1);
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
		Artista artist = this.artistService.findArtistaById(1);
		String newName = "Declan";
		artist.setName(newName);

		artist = this.artistService.findArtistaById(1);
		assertThat(newName.equals(artist.getName()));
	}

	// FIND ALL (COLLECTION ARTIST)
	@Test
	void shouldFindAllArtist() {
		Collection<Artista> listArtist = this.artistService.findAll();
		assertThat(listArtist.size()).isEqualTo(13);
	}

	// FIND ARTIST BY ID
	@Test
	void shouldFindArtistWithCorrectId() throws Exception {
		Artista artist3 = this.artistService.findArtistaById(3);
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
		//artist.setFestivales(fa);

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
	void shouldDeleteArtist() throws Exception {
		Collection<Artista> listArtist = this.artistService.findAll();
		int tamaño = listArtist.size();
		Artista artist = this.artistService.findArtistaById(4);

		this.artistService.delete(artist);

		listArtist = this.artistService.findAll();
		assertThat(listArtist.size()).isEqualTo(tamaño - 1);

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
		//artist.setFestivales(fa);
		assertThrows(ConstraintViolationException.class, () -> {
			this.artistService.save(artist);
		});
	}

}

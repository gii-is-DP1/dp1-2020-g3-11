package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat;    
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.Concert;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.service.exceptions.ConcertOutOfDateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ConcertServiceTests {
	@Autowired
	protected ConcertService concertService;
	
	@Autowired
	protected ArtistaService artistService;	
	
	@Autowired
	protected RecintoService recintService;	
	
	@Autowired
	protected FestivalService festivalService;	


	@Test
	void shouldFindConcertById() throws Exception {
		Concert concert2 = this.concertService.findById(2);
		assertThat(concert2.getFecha()).isEqualTo(LocalDate.of(2021, 7, 25));
		assertThat(concert2.getFestival().getId()).isEqualTo(2);
	}


@Test
void shoudFindAllConcert() throws Exception {
	List<Concert> concerts = (List<Concert>) this.concertService.findAll();
	assertThat((concerts.size() == 4)).isTrue();
}


@Test
void shoudFindAllConcertFestivalId() throws Exception {
	List<Concert> concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
	assertThat((concerts.size() == 3)).isTrue();
}


	@Test
	@Transactional
	void shouldInsertNewConcert() throws Exception {
		List<Concert> concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
		int size = concerts.size();
		Artista artist = this.artistService.findArtistaById(2);
		Recinto rec = this.recintService.findById(5);
		Festival fest = this.festivalService.findFestivalById(2).get();

		Concert concert = new Concert();
		concert.setFecha(LocalDate.of(2021, 07, 25));
		concert.setHoraCom(LocalDateTime.of(2021, 07, 25, 23, 40));
		concert.setHoraFin(LocalDateTime.of(2021, 07, 26, 01, 40));
		concert.setArtista(artist);
		concert.setRecinto(rec);
		concert.setFestival(fest);
		
		try {
			this.concertService.save(concert);
		} catch (Exception ex) {
			Logger.getLogger(ConcertServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
		assertThat(concerts.size()).isEqualTo(size + 1);
		assertThat(concert.getId()).isNotNull();
	}


	@Test
	@Transactional
	void shouldThrowExceptionInsertingNewConcertNullParameter() throws Exception {
		Concert concert = new Concert();
		
		Recinto rec = this.recintService.findById(5);
		Festival fest = this.festivalService.findFestivalById(2).get();
		
		concert.setFecha(LocalDate.of(2021, 07, 25));
		concert.setHoraCom(LocalDateTime.of(2021, 07, 25, 23, 40));
		concert.setHoraFin(LocalDateTime.of(2021, 07, 26, 01, 40));
		concert.setArtista(null);
		concert.setRecinto(rec);
		concert.setFestival(fest);
		
		assertThrows(ConstraintViolationException.class, () -> {
			this.concertService.save(concert);
		});
	}

	@Test
	@Transactional
	public void shouldThrowExceptionInsertingConcertsWithWrongDates() throws DataAccessException {
		Concert concert = new Concert();
		Artista artist = this.artistService.findArtistaById(2);
		Recinto rec = this.recintService.findById(5);
		Festival fest = this.festivalService.findFestivalById(2).get();
		concert.setFecha(LocalDate.of(2021, 07, 25));
		concert.setHoraCom(LocalDateTime.of(2021, 07, 25, 23, 40));
		concert.setHoraFin(LocalDateTime.of(2021, 07, 26, 01, 40));
		concert.setArtista(artist);
		concert.setRecinto(rec);
		concert.setFestival(fest);
		try {
			this.concertService.save(concert);
		} catch (ConcertOutOfDateException e) {
			// Wrongs dates!
			e.printStackTrace();
		}
		
		Concert concert2 = new Concert();
		Artista artist2 = this.artistService.findArtistaById(2);
		Recinto rec2 = this.recintService.findById(5);
		Festival fest2 = this.festivalService.findFestivalById(2).get();
		concert2.setFecha(LocalDate.of(2021, 07, 25));
		concert2.setHoraCom(LocalDateTime.of(2021, 8, 25, 23, 40));
		concert2.setHoraFin(LocalDateTime.of(2021, 8, 26, 01, 40));
		concert2.setArtista(artist2);
		concert2.setRecinto(rec2);
		concert2.setFestival(fest2);
		Assertions.assertThrows(ConcertOutOfDateException.class, () ->{
			this.concertService.save(concert2);
		});		
	}

	@Test
	@Transactional
	void shouldUpdateConcert() throws DataAccessException, ConcertOutOfDateException {
		Concert concert = this.concertService.findById(2);
		LocalDateTime horaCom = concert.getHoraCom();
		horaCom.plusMinutes(10);
		concert.setHoraCom(horaCom);
		this.concertService.save(concert);

		// retrieving new name from database
		concert = this.concertService.findById(2);
		assertThat(concert.getHoraCom()).isEqualTo(horaCom);
	}
	

	@Test
	@Transactional
	void shouldThrowExceptionUpdatingConcertWithWrongDates() throws DataAccessException, ConcertOutOfDateException {
		Concert concert = this.concertService.findById(2);
		LocalDateTime horaFin = concert.getHoraFin();
		concert.setHoraFin(horaFin.plusDays(10));

		Assertions.assertThrows(ConcertOutOfDateException.class, () ->{
			this.concertService.save(concert);
		});		
	}
	

	@Test
	@Transactional
	void shouldDeleteConcert() throws Exception {
		List<Concert> concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
		int size = concerts.size();
		Concert concert = this.concertService.findById(2);

		this.concertService.delete(concert);

		concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
		assertThat(concerts.size()).isEqualTo(size - 1);

	}
}

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
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.service.exceptions.ConcertOutOfDateException;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following
 * services provided by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary
 * set up time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning
 * that we don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ConcertServiceTests#clinicService clinicService}</code> instance variable,
 * which uses autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is
 * executed in its own transaction, which is automatically rolled back by
 * default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script.
 * <li>An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean
 * lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

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

//      FIND CONCERT BY ID

	@Test
	void shouldFindConcertById() throws Exception {
		Concert concert2 = this.concertService.findById(2);
		assertThat(concert2.getFecha()).isEqualTo(LocalDate.of(2021, 7, 26));
		assertThat(concert2.getFestival().getId()).isEqualTo(2);
	}

//  FIND ALL CONCERTS

@Test
void shoudFindAllConcert() throws Exception {
	List<Concert> concerts = (List<Concert>) this.concertService.findAll();
	assertThat((concerts.size() == 3)).isTrue();
}

//  FIND ALL CONCERTS BY FESTIVAL ID

@Test
void shoudFindAllConcertFestivalId() throws Exception {
	List<Concert> concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
	assertThat((concerts.size() == 3)).isTrue();
}


// 		INSERT NEW CONCERT 
	@Test
	@Transactional
	void shouldInsertNewConcert() throws Exception {
		List<Concert> concerts = (List<Concert>) this.concertService.findAllConcertsByFestivalId(2);
		int size = concerts.size();
		Artista artist = this.artistService.findArtistaById(2);
		Recinto rec = this.recintService.findById(5).get();
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

//		INSERT NEW NULL CONCERT 

	@Test
	@Transactional
	void shouldThrowExceptionInsertingNewConcertNullParameter() throws Exception {
		Concert concert = new Concert();
		
		Recinto rec = this.recintService.findById(5).get();
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
//	INSERT NEW CONCERT WITH EXCEPTION 

	@Test
	@Transactional
	public void shouldThrowExceptionInsertingConcertsWithWrongDates() throws DataAccessException {
		Concert concert = new Concert();
		Artista artist = this.artistService.findArtistaById(2);
		Recinto rec = this.recintService.findById(5).get();
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
		Recinto rec2 = this.recintService.findById(5).get();
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

//UPDATE CONCERT
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
	
	//UPDATE CONCERT WITH EXCEPTION

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
	
	//DELETE CONCERT

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

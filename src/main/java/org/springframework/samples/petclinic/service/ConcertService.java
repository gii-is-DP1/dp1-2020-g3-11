package org.springframework.samples.petclinic.service;

import java.util.Collection;  
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.repository.ConcertRepository;
import org.springframework.samples.petclinic.service.exceptions.ConcertOutOfDateException;
import org.springframework.samples.petclinic.service.exceptions.NumberConcertsException;
import org.springframework.samples.petclinic.web.ConcertValidator;
import org.springframework.stereotype.Service;

@Service
public class ConcertService {

	@Autowired
	ConcertRepository concertRepository;

	
	public Collection<Concert> findAll() throws DataAccessException{
		return concertRepository.findAll();
	}

	public Concert findById(int id) throws DataAccessException {
		return concertRepository.findById(id).get();
	}

	public void delete(Concert concierto) throws DataAccessException {
		concertRepository.deleteById(concierto.getId());

	}

	public void save(@Valid Concert concert) throws DataAccessException, ConcertOutOfDateException, NumberConcertsException {
//		Integer concertsTogether= ConcertValidator.conciertosAVez(this.concertRepository.findAllConciertosByRecintoId(concert.getRecinto().getId()), concert);
		if(concert.getHoraCom().toLocalDate().isBefore(concert.getFestival().getFechaCom()) ||
				concert.getHoraFin().toLocalDate().isAfter(concert.getFestival().getFechaFin())){
			throw new ConcertOutOfDateException();
		}
//			else if(concert.getRecinto().getNumMaxEscenarios()<concertsTogether){
//			
//			throw new NumberConcertsException();
//		}
		else {
			concertRepository.save(concert);

		}

	}
	
	public Collection<Concert> findAllConcertsByFestivalId(int festivalId) throws DataAccessException {
		return concertRepository.findAllConcertsByFestivalId(festivalId);
	}
	
}

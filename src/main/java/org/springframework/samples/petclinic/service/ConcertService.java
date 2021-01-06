package org.springframework.samples.petclinic.service;

import java.util.Collection;    

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.repository.ConcertRepository;
import org.springframework.samples.petclinic.service.exceptions.ConcertOutOfDateException;
import org.springframework.stereotype.Service;

@Service
public class ConcertService {

	ConcertRepository concertRepository;

	
	public Collection<Concert> findAll() throws DataAccessException{
		return concertRepository.findAll();
	}
	
	@Autowired
	public ConcertService(ConcertRepository concertRepository)  throws DataAccessException{
		this.concertRepository = concertRepository;
	}

	public Concert findById(int id) throws DataAccessException {
		return concertRepository.findById(id).get();
	}

	public void delete(Concert concierto) throws DataAccessException {
		concertRepository.deleteById(concierto.getId());

	}

	public void save(@Valid Concert concert) throws DataAccessException, ConcertOutOfDateException {
		if(concert.getHoraCom().toLocalDate().isBefore(concert.getFestival().getFechaCom()) ||
				concert.getHoraFin().toLocalDate().isAfter(concert.getFestival().getFechaFin())){
			throw new ConcertOutOfDateException();
		}
		else {
			concertRepository.save(concert);

		}

	}
	
	public Collection<Concert> findAllConcertsByFestivalId(int festivalId) throws DataAccessException {
		return concertRepository.findAllConcertsByFestivalId(festivalId);
	}
	
}

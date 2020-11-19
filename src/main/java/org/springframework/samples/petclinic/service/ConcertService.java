package org.springframework.samples.petclinic.service;

import java.util.Collection;  
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.repository.ConcertRepository;
import org.springframework.stereotype.Service;

@Service
public class ConcertService {

	@Autowired
	ConcertRepository concertRepository;

	
	public Collection<Concert> findAll() throws DataAccessException{
		return concertRepository.findAll();
	}

	public Optional<Concert> findById(int id) throws DataAccessException {
		return concertRepository.findById(id);
	}

	public void delete(Concert concierto) throws DataAccessException {
		concertRepository.deleteById(concierto.getId());

	}

	public void save(@Valid Concert concierto) throws DataAccessException {
		concertRepository.save(concierto);

	}
	
	public Collection<Concert> findAllConcertsByFestivalId(int festivalId) throws DataAccessException {
		return concertRepository.findAllConcertsByFestivalId(festivalId);
	}
	
}

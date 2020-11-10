package org.springframework.samples.petclinic.service;

import java.util.Collection; 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.samples.petclinic.repository.ConciertoRepository;
import org.springframework.stereotype.Service;

@Service
public class ConciertoService {

	@Autowired
	ConciertoRepository conciertoRepo;

	
	public Collection<Concierto> findAll() {
		return conciertoRepo.findAll();
	}

	public Optional<Concierto> findById(int id) {
		return conciertoRepo.findById(id);
	}

	public void delete(Concierto concierto) {
		conciertoRepo.deleteById(concierto.getId());

	}

	public void save(@Valid Concierto concierto) {
		conciertoRepo.save(concierto);

	}

}

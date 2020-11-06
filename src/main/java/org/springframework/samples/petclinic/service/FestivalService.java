package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.repository.FestivalRepository;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {

	@Autowired
	FestivalRepository festivalRepo;

	
	public Collection<Festival> findAll() {
		return festivalRepo.findAll();
	}

	public Optional<Festival> findById(int id) {
		return festivalRepo.findById(id);
	}

	public void delete(Festival festival) {
		festivalRepo.deleteById(festival.getId());

	}

	public void save(@Valid Festival festival) {
		festivalRepo.save(festival);

	}

}

package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.repository.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FestivalService {

	@Autowired
	FestivalRepository festivalRepo;

//	public Collection<Artista> findAllArtistasByFestivalId(int festivalId){
//		return festivalRepo.findAllArtistasByFestivalId(festivalId);
//	}

	public Collection<Festival> findAll() {
		return festivalRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Festival findFestivalByAdminId(Integer id) {
		return festivalRepo.findFestivalByAdminId(id);
	}

	public void delete(Festival festival) throws DataAccessException {
		festivalRepo.deleteById(festival.getId());

	}

	public void save(@Valid Festival festival) {
		festivalRepo.save(festival);
	}

	@Transactional(readOnly = true)
	public Festival findFestivalByName(String name) throws DataAccessException {
		return festivalRepo.findFestivalByName(name);
	}

	@Transactional(readOnly = true)
	public Optional<Festival> findFestivalById(int id) throws DataAccessException {
		return festivalRepo.findById(id);
	}

	@Transactional
	public LocalDate findStartDateFestival(int id) throws DataAccessException {
		return festivalRepo.findStartDateFestival(id);
	}

	@Transactional
	public LocalDate findEndDateFestival(int id) throws DataAccessException {
		return festivalRepo.findEndDateFestival(id);
	}
	
	@Transactional(readOnly = true)
	public Festival findFestivalById2(int id) throws DataAccessException {
		return festivalRepo.findById(id).get();
	}

}

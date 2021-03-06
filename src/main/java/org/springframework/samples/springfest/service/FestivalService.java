package org.springframework.samples.springfest.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.repository.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FestivalService {

	FestivalRepository festivalRepo;

	@Autowired
	public FestivalService(FestivalRepository festivalRepo) throws DataAccessException {
		this.festivalRepo = festivalRepo;
	}

	public Collection<Festival> findAll() {
		return festivalRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Festival findFestivalByAdminId(Integer id) {
		return festivalRepo.findFestivalByAdminId(id);
	}

	public void save(@Valid Festival festival) {
		festivalRepo.save(festival);
	}

	public void reducirEntradasRestantes(@Valid Festival festival) {
		FestivalRepository.reducirEntradasrestantes(festival);
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

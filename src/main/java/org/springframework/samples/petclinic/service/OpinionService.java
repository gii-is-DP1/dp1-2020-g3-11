package org.springframework.samples.petclinic.service;

import java.time.LocalDateTime;
import java.util.Collection; 
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.repository.OpinionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpinionService {
	@Autowired
	OpinionRepository opinionRepo;

	public Collection<Opinion> findAll() throws DataAccessException {
		return opinionRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Opinion> findById(int id) throws DataAccessException {
		return opinionRepo.findById(id);
	}

	@Transactional
	public void delete(Opinion opinion) throws DataAccessException {
		opinionRepo.deleteById(opinion.getId());

	}

	@Transactional
	public void save(Opinion opinion) throws DataAccessException {
		opinion.setFecha(LocalDateTime.of(LocalDateTime.now().getYear(), 
				LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(),
				LocalDateTime.now().getHour(), LocalDateTime.now().getMinute()));
		opinionRepo.save(opinion);

	}

	@Transactional
	public Collection<Opinion> findOpinionsByFestivalId(int festivalId) {
		Collection<Opinion> l= opinionRepo.findOpinionsByFestivalId(festivalId);
		if(l.size()>=5) {
			l= opinionRepo.findOpinionsByFestivalId(festivalId).stream().collect(Collectors.toList()).subList(0, 5);
		}
		return l;
	}
	
	@Transactional
	public Integer average(int festivalId) {
		return opinionRepo.averageOpinionByFestivalId(festivalId);
	}

}

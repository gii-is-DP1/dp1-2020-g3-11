package org.springframework.samples.springfest.service;

import java.util.Collection; 
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Opinion;
import org.springframework.samples.springfest.repository.OpinionRepository;
import org.springframework.samples.springfest.service.exceptions.OpinionFestivalDateException;
import org.springframework.samples.springfest.service.exceptions.OpinionNotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpinionService {
	
	OpinionRepository opinionRepo;

	@Autowired
	public OpinionService(OpinionRepository opinionRepository) throws DataAccessException {
		this.opinionRepo = opinionRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Opinion> findAll() throws DataAccessException {
		return opinionRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Opinion findById(int id) throws DataAccessException {
		return opinionRepo.findById(id).get();
	}

	@Transactional
	public void delete(Opinion opinion) throws DataAccessException {
		opinionRepo.deleteById(opinion.getId());

	}

	@Transactional
	public void save(Opinion opinion)
			throws DataAccessException, OpinionNotAllowedException, OpinionFestivalDateException {


			if (opinion.getFecha().isAfter(opinion.getFestival().getFechaFin().plusDays(1).atTime(00, 00))) {

				opinionRepo.save(opinion);
			} else {
				throw new OpinionFestivalDateException();
			}

			if (opinion.getOpinionUsuario().getEntradas().stream()
					.anyMatch(p -> p.getFestival().equals(opinion.getFestival()))) {

				opinionRepo.save(opinion);

			} else {

				throw new OpinionNotAllowedException();
			}
		}

	@Transactional(readOnly = true)
	public Collection<Opinion> findOpinionsByFestivalId(int festivalId) {
		Collection<Opinion> l = opinionRepo.findOpinionsByFestivalId(festivalId);
		if (l.size() >= 5) {
			l = opinionRepo.findOpinionsByFestivalId(festivalId).stream().collect(Collectors.toList()).subList(0, 5);
		}
		return l;
	}

	@Transactional
	public Integer average(int festivalId) {
		return opinionRepo.averageOpinionByFestivalId(festivalId);
	}

}

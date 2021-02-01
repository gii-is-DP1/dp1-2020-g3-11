package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.repository.OpinionRepository;
import org.springframework.samples.petclinic.service.exceptions.OpinionFestivalDateException;
import org.springframework.samples.petclinic.service.exceptions.OpinionNotAllowedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpinionService {
	@Autowired
	OpinionRepository opinionRepo;

	@Transactional(readOnly = true)
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

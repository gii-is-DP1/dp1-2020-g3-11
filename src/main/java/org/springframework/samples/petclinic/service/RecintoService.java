package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.samples.petclinic.repository.RecintoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecintoService {
	@Autowired
	RecintoRepository recintoRepo;

	public Collection<Recinto> findAll() throws DataAccessException {
		return recintoRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Recinto> findById(int id) throws DataAccessException {
		return recintoRepo.findById(id);
	}

	@Transactional
	public void delete(Recinto recinto) throws DataAccessException {
		recintoRepo.deleteById(recinto.getId());

	}

	@Transactional
	public void save(Recinto recinto) throws DataAccessException {
		recintoRepo.save(recinto);

	}
	
	@Transactional(readOnly = true)
	public TipoRecinto findRecintoType(String recinto) throws DataAccessException {
		return recintoRepo.findRecintoTypeByName(recinto);
	}
	
	@Transactional(readOnly = true)
	public Collection<TipoRecinto> findRecintoTypes() throws DataAccessException {
		return recintoRepo.findRecintoTypes();
	}
	
	@Transactional(readOnly = true)
	public Recinto findRecintoByType(TipoRecinto tipo) throws DataAccessException {
		return recintoRepo.findRecintoByType(tipo);
	}
	
	@Transactional(readOnly = true)
	public Collection<String> findAllRecintos() throws DataAccessException {
		return recintoRepo.findAllRecintos();
	} 
	
	@Transactional(readOnly = true)
	public Recinto findRecintoByName(String name) throws DataAccessException {
		return recintoRepo.findRecintoByName(name);
	}

	@Transactional(readOnly = true)
	public Collection<Recinto> findAllRecintosByFestivalId(int festivalId) throws DataAccessException{
		return recintoRepo.findAllRecintosByFestivalId(festivalId);
	}

	@Transactional(readOnly = true)
	public Recinto findByRecintoIdFestivalId(int festivalId, int recintoId) {
		return recintoRepo.findByRecintoIdFestivalId(festivalId, recintoId);
	}

	public Collection<Concert> findAllConciertosById(int recintoId) {
		return recintoRepo.findAllConciertosById(recintoId);
	}
	
	


}

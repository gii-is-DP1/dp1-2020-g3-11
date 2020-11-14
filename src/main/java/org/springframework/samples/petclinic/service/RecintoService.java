package org.springframework.samples.petclinic.service;

import java.util.Collection; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.samples.petclinic.repository.RecintoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecintoService {
	@Autowired
	RecintoRepository recintoRepo;

	public Collection<Recinto> findAll() {
		return recintoRepo.findAll();
	}

	public Optional<Recinto> findById(int id) {
		return recintoRepo.findById(id);
	}

	public void delete(Recinto recinto) {
		recintoRepo.deleteById(recinto.getId());

	}

	public void save(Recinto recinto) {
		recintoRepo.save(recinto);

	}
	
	public TipoRecinto findRecintoType(String recinto) {
		return recintoRepo.findRecintoTypeByName(recinto);
	}
	
	@Transactional(readOnly = true)
	public Collection<TipoRecinto> findRecintoTypes() throws DataAccessException {
		return recintoRepo.findRecintoTypes();
	}
	
	public Recinto findRecintoByType(TipoRecinto tipo) {
		return recintoRepo.findRecintoByType(tipo);
	}
	
	public Collection<String> findAllRecintos() {
		return recintoRepo.findAllRecintos();
	} 
	
	public Recinto findRecintoByName(String name) {
		return recintoRepo.findRecintoByName(name);
	}


}

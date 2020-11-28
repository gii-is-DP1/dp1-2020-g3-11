package org.springframework.samples.petclinic.service;

import java.util.Collection; 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;
import org.springframework.samples.petclinic.repository.EntradaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntradaService {
	
	@Autowired
	EntradaRepository entradaRepo;

	
	public Collection<Entrada> findAll() {
		return entradaRepo.findAll();
	}

	public Optional<Entrada> findById(int id) throws DataAccessException {
		return entradaRepo.findById(id);
	}
	
	public EntradaType findEntradaType(String entradatype) {
        return entradaRepo.findEntradaTypeByName(entradatype);
    }

	public void delete(Entrada entrada) {
		entradaRepo.deleteById(entrada.getId());

	}

	public void save(@Valid Entrada entrada) {
		entradaRepo.save(entrada);

	}
	
	public Collection<Entrada> findAllEntradasByFestivalId(int festivalId) throws DataAccessException{
		return entradaRepo.findAllEntradasByFestivalId(festivalId);
	}

	public Entrada findByEntradaIdFestivalId(int festivalId, int entradaId) {
		return entradaRepo.findByEntradaIdFestivalId(festivalId, entradaId);
	}
	
	@Transactional(readOnly = true)
    public Collection<String> findEntradaTypes() throws DataAccessException {
        return entradaRepo.findEntradaTypes();
    }

}

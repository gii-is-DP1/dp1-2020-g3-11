package org.springframework.samples.springfest.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.EntradaType;
import org.springframework.samples.springfest.repository.EntradaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntradaService {

	@Autowired
	EntradaRepository entradaRepo;

	public Collection<Entrada> findAll() {
		return entradaRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Entrada> findById(int id) throws DataAccessException {
		return entradaRepo.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Entrada> findEntradasCompradasUsuario(int id) throws DataAccessException {
		return entradaRepo.findEntradasCompradasUsuario(id);
	}

	@Transactional(readOnly = true)
	public EntradaType findEntradaType(String entradatype) {
		return entradaRepo.findEntradaTypeByName(entradatype);
	}

	@Transactional
	public void delete(Entrada entrada) throws DataAccessException {
		entradaRepo.deleteById(entrada.getId());
	}

	@Transactional
	public void save(@Valid Entrada entrada) throws DataAccessException {
		entradaRepo.save(entrada);
	}

	@Transactional(readOnly = true)
	public Collection<Entrada> findAllEntradasByFestivalId(int festivalId) throws DataAccessException {
		return entradaRepo.findAllEntradasByFestivalId(festivalId);
	}

	@Transactional(readOnly = true)
	public Entrada findByEntradaIdFestivalId(int festivalId, int entradaId) {
		return entradaRepo.findByEntradaIdFestivalId(festivalId, entradaId);
	}

	@Transactional(readOnly = true)
	public Collection<String> findEntradaTypes() throws DataAccessException {
		return entradaRepo.findEntradaTypes();
	}
	
	@Transactional(readOnly = true)
	public Entrada findEntradaById2(int id) throws DataAccessException {
		return entradaRepo.findById(id).get();
	}
}

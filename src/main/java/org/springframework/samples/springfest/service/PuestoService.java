package org.springframework.samples.springfest.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Puesto;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoPuesto;
import org.springframework.samples.springfest.model.TipoTamaño;
import org.springframework.samples.springfest.repository.PuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuestoService {

	@Autowired
	PuestoRepository puestoRepository;

	@Transactional (readOnly = true)
	public Collection<Puesto> findAll() throws DataAccessException {
		return puestoRepository.findAll();
	}

	@Transactional (readOnly = true)
	public Optional<Puesto> findById(int id) throws DataAccessException {
		return puestoRepository.findById(id);
	}

	@Transactional
	public void save(Puesto puesto) throws DataAccessException {
		puestoRepository.save(puesto);
	}

	@Transactional
	public void delete(Puesto puesto) throws DataAccessException {
		puestoRepository.deleteById(puesto.getId());

	}

	@Transactional(readOnly = true)
	public TipoPuesto findPuestoType(String nombre) throws DataAccessException {
		return puestoRepository.findPuestoTypeByName(nombre);
	}

	@Transactional(readOnly = true)
	public TipoTamaño findTamañoType(String name) throws DataAccessException {
		return puestoRepository.findTipoTamañoByName(name);
	}

	@Transactional(readOnly = true)
	public Collection<Puesto> findAllPuestosByFestivalId(int festivalId) throws DataAccessException {
		return puestoRepository.findAllPuestosByFestivalId(festivalId);
	}

	@Transactional(readOnly = true)
	public Collection<TipoPuesto> findTiposPuesto() {
		return puestoRepository.findTiposPuesto();
	}

	@Transactional(readOnly = true)
	public Collection<TipoTamaño> findTiposTamaño() {
		return puestoRepository.findTiposTamaño();
	}

	@Transactional(readOnly = true)
	public Recinto findRecintoById(Integer id) {
		return puestoRepository.findRecintoById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Puesto> findPuestosLibres(Integer festivalId){
		return puestoRepository.findPuestosLibres(festivalId);
	}

	@Transactional(readOnly = true)
	public Collection<Puesto> findAllPuestosBySponsorId(Integer id) {
		return puestoRepository.findAllPuestosBySponsorId(id);
	}
}

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;
import org.springframework.samples.petclinic.repository.PuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuestoService {

	@Autowired
	PuestoRepository puestoRepository;
	
	@Transactional
	public Collection<Puesto> findAll() throws DataAccessException {
		return puestoRepository.findAll();
	}
	
	public Optional<Puesto> findById(int id) throws DataAccessException {
		return puestoRepository.findById(id);
	}
	
	
	@Transactional
	public void save(Puesto puesto) throws DataAccessException {
		puestoRepository.save(puesto);
	}
	
	public void delete(Puesto puesto) throws DataAccessException {
		puestoRepository.deleteById(puesto.getId());

	}
	
	@Transactional
	public TipoPuesto findPuestoType(String nombre) throws DataAccessException {
		return puestoRepository.findPuestoTypeByName(nombre);
	}

	@Transactional
	public TipoTamaño findTamañoType(String name) throws DataAccessException{
		return puestoRepository.findTipoTamañoByName(name);
	}
	
	public Collection<Puesto> findAllPuestosByFestivalId(int festivalId) throws DataAccessException{
		return puestoRepository.findAllPuestosByFestivalId(festivalId);
	}
	
	
	public Collection<TipoPuesto> findTiposPuesto() {
		return puestoRepository.findTiposPuesto();
	}
	
	public Collection<TipoTamaño> findTiposTamaño() {
		return puestoRepository.findTiposTamaño();
	}
}

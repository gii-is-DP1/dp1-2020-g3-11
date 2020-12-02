package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	@Transactional
	public void save(Puesto puesto) throws DataAccessException {
		puestoRepository.save(puesto);
	}
	
	@Transactional(readOnly = true)
	public TipoPuesto findPuestoType(String nombre) throws DataAccessException {
		return puestoRepository.findPuestoTypeByName(nombre);
	}

	@Transactional
	public TipoTamaño findTamañoType(String name) {
		return puestoRepository.findTipoTamañoByName(name);
	}
	
	@Transactional
	public Collection<Puesto> findAllPuestosByFestivalId(int festivalId) {
		return puestoRepository.findAllPuestosByFestivalId(festivalId);
	}
	
	
	public Collection<TipoPuesto> findTiposPuesto() {
		return puestoRepository.findTiposPuesto();
	}
	
	public Collection<TipoTamaño> findTiposTamaño() {
		return puestoRepository.findTiposTamaño();
	}
}

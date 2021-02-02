package springfest.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springfest.model.Puesto;
import springfest.model.Recinto;
import springfest.model.TipoPuesto;
import springfest.model.TipoTamaño;
import springfest.repository.PuestoRepository;

@Service
public class PuestoService {

	@Autowired
	PuestoRepository puestoRepository;

	@Transactional
	public Collection<Puesto> findAll() throws DataAccessException {
		return puestoRepository.findAll();
	}

	@Transactional
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
	
	public Collection<Puesto> findPuestosLibres(Integer festivalId){
		return puestoRepository.findPuestosLibres(festivalId);
	}

	public Collection<Puesto> findAllPuestosBySponsorId(Integer id) {
		return puestoRepository.findAllPuestosBySponsorId(id);
	}
}

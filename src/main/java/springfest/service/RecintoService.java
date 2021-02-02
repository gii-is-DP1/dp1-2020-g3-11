package springfest.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springfest.model.Concert;
import springfest.model.Puesto;
import springfest.model.Recinto;
import springfest.model.TipoRecinto;
import springfest.repository.ArtistaRepository;
import springfest.repository.RecintoRepository;

@Service
public class RecintoService {

	RecintoRepository recintoRepo;
	
	@Autowired
	public RecintoService(RecintoRepository recintoRepo) throws DataAccessException {
		this.recintoRepo = recintoRepo;
	}

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

	@Transactional(readOnly = true)
	public Collection<Concert> findAllConciertosById(int recintoId) {
		return recintoRepo.findAllConciertosById(recintoId);
	}

	@Transactional(readOnly = true)
	public Collection<Puesto> findAllPuestosById(int recintoId) {
		return recintoRepo.findAllPuestosById(recintoId);
	}

	@Transactional(readOnly = true)
	public Recinto findRecintoById(int id) throws DataAccessException {
		return recintoRepo.findById(id).get();
	}
	
	


}

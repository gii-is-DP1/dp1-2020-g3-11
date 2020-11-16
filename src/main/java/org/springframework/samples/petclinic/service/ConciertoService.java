package org.springframework.samples.petclinic.service;

import java.util.Collection; 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.samples.petclinic.repository.ConciertoRepository;
import org.springframework.stereotype.Service;

@Service
public class ConciertoService {

	@Autowired
	ConciertoRepository conciertoRepo;

	
	public Collection<Concierto> findAll() throws DataAccessException{
		return conciertoRepo.findAll();
	}

	public Optional<Concierto> findById(int id) throws DataAccessException {
		return conciertoRepo.findById(id);
	}

	public Artista findArtistaByName(String name) throws DataAccessException {
		return conciertoRepo.findArtistaByName(name);
	}
	
	public void delete(Concierto concierto) throws DataAccessException {
		conciertoRepo.deleteById(concierto.getId());

	}

	public void save(@Valid Concierto concierto) throws DataAccessException {
		conciertoRepo.save(concierto);

	}
	
	public Collection<Concierto> findAllConciertosByFestivalId(int festivalId) throws DataAccessException {
		return conciertoRepo.findAllConciertosByFestivalId(festivalId);
	}
	
	public Collection<String> findAllArtistas() throws DataAccessException {
		return conciertoRepo.findAllArtistas();
	} 
	
	public Collection<String> findAllRecintos() throws DataAccessException {
		return conciertoRepo.findAllRecintos();
	} 
	
	public Recinto findRecintoByName(String name) throws DataAccessException {
		return conciertoRepo.findRecintoByName(name);
	}
	
	public Collection<Recinto> findAllRecintosByFestivalId(int festivalId) throws DataAccessException {
		return conciertoRepo.findAllRecintosByFestivalId(festivalId);
	}
	
	public Collection<Concierto> findAllConciertosByRecintoId(int recintoId) throws DataAccessException {
		return conciertoRepo.findAllConciertosByRecintoId(recintoId);
	}
	

}

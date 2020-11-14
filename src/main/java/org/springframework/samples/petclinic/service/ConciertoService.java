package org.springframework.samples.petclinic.service;

import java.util.Collection; 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	
	public Collection<Concierto> findAll() {
		return conciertoRepo.findAll();
	}

	public Optional<Concierto> findById(int id) {
		return conciertoRepo.findById(id);
	}

	public Artista findArtistaByName(String name) {
		return conciertoRepo.findArtistaByName(name);
	}
	
	public void delete(Concierto concierto) {
		conciertoRepo.deleteById(concierto.getId());

	}

	public void save(@Valid Concierto concierto) {
		conciertoRepo.save(concierto);

	}
	
	public Collection<Concierto> findAllConciertosByFestivalId(int festivalId) {
		return conciertoRepo.findAllConciertosByFestivalId(festivalId);
	}
	
	public Collection<String> findAllArtistas() {
		return conciertoRepo.findAllArtistas();
	} 
	
	public Collection<String> findAllRecintos() {
		return conciertoRepo.findAllRecintos();
	} 
	
	public Recinto findRecintoByName(String name) {
		return conciertoRepo.findRecintoByName(name);
	}
	
	public Collection<Recinto> findAllRecintosByFestivalId(int festivalId) {
		return conciertoRepo.findAllRecintosByFestivalId(festivalId);
	}
	
	

}

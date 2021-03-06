package org.springframework.samples.springfest.service;

import java.util.Collection;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.GeneroType;
import org.springframework.samples.springfest.repository.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistaService {

	private ArtistaRepository artistaRepository;

	@Transactional(readOnly = true)
	public Collection<Artista> findAll() throws DataAccessException {
		return artistaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<Artista> getAll(Pageable pageable) throws DataAccessException {
		return artistaRepository.findAll(pageable);
	}

	@Autowired
	public ArtistaService(ArtistaRepository artistaRepository) throws DataAccessException {
		this.artistaRepository = artistaRepository;
	}

	@Transactional(readOnly = true)
	public Artista findArtistaById(int id) throws DataAccessException {
		return artistaRepository.findArtistaById(id);
	}
	
	@Transactional(readOnly = true)
	public Set<Artista> findArtistasByFestivalId(int festivalId) throws DataAccessException {
		return artistaRepository.findArtistasByFestivalId(festivalId);
	}

	@Transactional
	public void save(Artista artista) throws DataAccessException {
		artistaRepository.save(artista);
	}

	@Transactional(readOnly = true)
	public GeneroType findGeneroType(String genero) throws DataAccessException {
		return artistaRepository.findGeneroTypeByName(genero);
	}

	@Transactional(readOnly = true)
	public Collection<String> findAllArtistas() throws DataAccessException {
		return artistaRepository.findAllArtistas();
	}

	@Transactional(readOnly = true)
	public Artista findArtistaByName(String name) throws DataAccessException {
		return artistaRepository.findArtistaByName(name);
	}

	@Transactional(readOnly = true)
	public Collection<String> findGeneroTypes() throws DataAccessException {
		return artistaRepository.findGeneroTypes();
	}

	public boolean checkCorreoExists(String correo) {
		return artistaRepository.findArtistaByCorreo(correo).isPresent();
	}
	
	public boolean checkTelefonoExists(String telefono) {
		return artistaRepository.findArtistaByTelefono(telefono).isPresent();
	}

	public boolean checkNombreExists(String name) {
		return artistaRepository.findArtistaByName2(name).isPresent();
	}
	
}

/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.GeneroType;
import org.springframework.samples.petclinic.repository.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistaService {

	private ArtistaRepository artistaRepository;

	@Transactional(readOnly = true)
	public Collection<Artista> findAll() throws DataAccessException {
		return artistaRepository.findAll();
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

	public void save(@Valid Artista artista) throws DataAccessException {
		artistaRepository.save(artista);
	}

	public GeneroType findGeneroType(String genero) throws DataAccessException {
		return artistaRepository.findGeneroTypeByName(genero);
	}

	public Collection<String> findAllArtistas() throws DataAccessException {
		return artistaRepository.findAllArtistas();
	}

	public Artista findArtistaByName(String name) throws DataAccessException {
		return artistaRepository.findArtistaByName(name);
	}

	@Transactional
	public void delete(Artista artista) throws DataAccessException {
		artistaRepository.deleteById(artista.getId());
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

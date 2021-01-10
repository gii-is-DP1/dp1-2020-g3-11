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
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.GeneroType;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.repository.ArtistaRepository;


public interface ArtistaRepository extends CrudRepository<Artista, Integer> {

	Collection<Artista> findAll();

	
	@Query("SELECT gtype.name FROM GeneroType gtype ORDER BY gtype.name")
	List<String> findGeneroTypes() throws DataAccessException;
	
	@Query("SELECT gtype FROM GeneroType gtype where gtype.name = ?1")
	GeneroType findGeneroTypeByName(String genero);
	
	@Query("SELECT a.name FROM Artista a ORDER BY a.name")
	Collection<String> findAllArtistas();
	
	@Query("SELECT a FROM Artista a where a.name = ?1")
	Artista findArtistaByName(String name);
	
	@Query("SELECT distinct artista FROM Artista artista join artista.festivales af where af.id  =  ?1")
	Set<Artista> findArtistasByFestivalId(int festivalId);
	
	@Query("SELECT a FROM Artista a where a.id = ?1")
    Artista findArtistaById(int id);

	@Query("SELECT a FROM Artista a where a.correo = ?1")
	Optional<Artista> findArtistaByCorreo(String correo);	
	
	@Query("SELECT a FROM Artista a where a.telefono = ?1")
	Optional<Artista> findArtistaByTelefono(String telefono);

	//este es para la validacion de nombre unico
	@Query("SELECT a FROM Artista a where a.name = ?1")
	Optional<Artista> findArtistaByName2(String name);	
}

package org.springframework.samples.springfest.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.GeneroType;
import org.springframework.samples.springfest.repository.ArtistaRepository;


public interface ArtistaRepository extends PagingAndSortingRepository<Artista, Integer> {

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

	@Query("SELECT a FROM Artista a where a.name = ?1")
	Optional<Artista> findArtistaByName2(String name);	
}

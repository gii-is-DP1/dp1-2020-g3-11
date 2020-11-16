package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.samples.petclinic.model.Recinto;

public interface ConciertoRepository extends CrudRepository<Concierto, Integer> {

	Collection<Concierto> findAll();
	
	Collection<Concierto> findAllConciertosByFestivalId(int festivalId);
	
	@Query("SELECT a.name FROM Artista a ORDER BY a.name")
	Collection<String> findAllArtistas() throws DataAccessException;

	@Query("SELECT a.name FROM Recinto a ORDER BY a.name")
	Collection<String> findAllRecintos() throws DataAccessException;
	
	@Query("SELECT a FROM Artista a where a.name = ?1")
	Artista findArtistaByName(String name) throws DataAccessException;

	@Query("SELECT a FROM Recinto a where a.name = ?1")
	Recinto findRecintoByName(String name) throws DataAccessException;
	
	@Query("select r from Recinto r where r.festival.id = ?1")
	public Collection<Recinto> findAllRecintosByFestivalId(int festivalId) throws DataAccessException;

	@Query("select c from Concierto c where c.recinto.id = ?1")
	Collection<Concierto> findAllConciertosByRecintoId(int recintoId) throws DataAccessException;

}

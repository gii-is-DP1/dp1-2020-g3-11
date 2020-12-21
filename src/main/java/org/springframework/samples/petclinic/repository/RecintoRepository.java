package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;

public interface RecintoRepository extends CrudRepository<Recinto, Integer> {
	
	Collection<Recinto> findAll();
	
	@Query("SELECT rtype FROM TipoRecinto rtype ORDER BY rtype.name")
	List<TipoRecinto> findRecintoTypes() throws DataAccessException;
	
	@Query("SELECT rtype FROM TipoRecinto rtype where rtype.name = ?1")
	TipoRecinto findRecintoTypeByName(String recinto);
	
	@Query("SELECT r FROM Recinto r where r.tipoRecinto = ?1")
	Recinto findRecintoByType(TipoRecinto tipo);
	
	@Query("SELECT a.name FROM Recinto a ORDER BY a.name")
	Collection<String> findAllRecintos();
	
	@Query("SELECT a FROM Recinto a where a.name = ?1")
	Recinto findRecintoByName(String name);
	
	@Query("SELECT r FROM Recinto r where r.festival.id = ?1")
	Collection<Recinto> findAllRecintosByFestivalId(int festivalId);
	
	@Query("select r from Recinto r where r.festival.id = ?1 and r.id = ?2")
	Recinto findByRecintoIdFestivalId(int festivalId, int recintoId);

	@Query("select c from Concert c where c.recinto.id = ?1")
	Collection<Concert> findAllConciertosById(int recintoId);

	@Query("select p from Puesto p where p.recinto.id = ?1")
	Collection<Puesto> findAllPuestosById(int recintoId);

	
}
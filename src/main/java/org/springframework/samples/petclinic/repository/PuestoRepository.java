package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;

public interface PuestoRepository extends CrudRepository<Puesto, Integer> {

	Collection<Puesto> findAll();
	
	@Query("SELECT ptype FROM TipoPuesto ptype where ptype.name = ?1")
	TipoPuesto findPuestoTypeByName(String name);

	@Query("SELECT ptype FROM TipoTamaño ptype where ptype.name = ?1")
	TipoTamaño findTipoTamañoByName(String name);

//	@Query("SELECT p FROM Puesto p where p.festival.id = ?1")
	Collection<Puesto> findAllPuestosByFestivalId(int festivalId);

	@Query("SELECT ttype FROM TipoTamaño ttype ORDER BY ttype.name")
	Collection<TipoTamaño> findTiposTamaño();
	
	@Query("SELECT ptype FROM TipoPuesto ptype ORDER BY ptype.name")
	Collection<TipoPuesto> findTiposPuesto();
	
}

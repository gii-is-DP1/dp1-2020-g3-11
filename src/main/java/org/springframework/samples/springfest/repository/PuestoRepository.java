package org.springframework.samples.springfest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.Puesto;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoPuesto;
import org.springframework.samples.springfest.model.TipoTamaño;

public interface PuestoRepository extends CrudRepository<Puesto, Integer> {

	Collection<Puesto> findAll();
	
	@Query("SELECT ptype FROM TipoPuesto ptype where ptype.name = ?1")
	TipoPuesto findPuestoTypeByName(String name);

	@Query("SELECT ptype FROM TipoTamaño ptype where ptype.name = ?1")
	TipoTamaño findTipoTamañoByName(String name);

	Collection<Puesto> findAllPuestosByFestivalId(int festivalId);

	@Query("SELECT ttype FROM TipoTamaño ttype ORDER BY ttype.name")
	Collection<TipoTamaño> findTiposTamaño();
	
	@Query("SELECT ptype FROM TipoPuesto ptype ORDER BY ptype.name")
	Collection<TipoPuesto> findTiposPuesto();

	@Query("SELECT r FROM Recinto r where r.id = ?1")
	Recinto findRecintoById(Integer id);

	@Query("SELECT p FROM Puesto p where p.sponsor.id = null AND p.festival.id = ?1")
	Collection<Puesto> findPuestosLibres(Integer festivalId);

	@Query("SELECT p FROM Puesto p where p.sponsor.id = ?1")
	Collection<Puesto> findAllPuestosBySponsorId(Integer id);
	
}

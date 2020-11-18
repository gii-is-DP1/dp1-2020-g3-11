package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;



public interface EntradaRepository extends CrudRepository<Entrada, Integer>{

	Collection<Entrada> findAll();
	
	@Query("SELECT etype FROM EntradaType etype where etype.name = ?1")
    EntradaType findEntradaTypeByName(String entradatype);

	
	@Query("SELECT etype.name FROM EntradaType etype ORDER BY etype.name")
    List<String> findEntradaTypes() throws DataAccessException;
}
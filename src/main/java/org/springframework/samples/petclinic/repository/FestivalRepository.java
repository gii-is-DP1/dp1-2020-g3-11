package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.petclinic.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Integer> {

	Collection<Festival> findAll();

	@Query("SELECT f.name FROM Festival f ORDER BY f.name")
	Collection<String> findAllFestival();
	
	
	@Query("SELECT f FROM Festival f where f.name =?1")
	Festival findFestivalByName(String name);
	
	
//	@Query("SELECT f FROM Festival f where f.date =?1")
//	Festival findFestivalByDate(String name);
	
}

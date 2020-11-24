package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
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
	
	
	@Query("SELECT fechaCom FROM Festival f where f.id =?1")
	LocalDate findStartDateFestival(int id);
	
	@Query("SELECT fechaFin FROM Festival f where f.id =?1")
	LocalDate findEndDateFestival(int id);

	
}

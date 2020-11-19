package org.springframework.samples.petclinic.repository;

import java.util.Collection; 

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Integer> {

	Collection<Concert> findAll();
	
	Collection<Concert> findAllConcertsByFestivalId(int festivalId);
	

}

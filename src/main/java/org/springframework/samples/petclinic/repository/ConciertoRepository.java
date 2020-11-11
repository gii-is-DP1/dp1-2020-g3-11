package org.springframework.samples.petclinic.repository;

import java.util.Collection; 

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Concierto;

public interface ConciertoRepository extends CrudRepository<Concierto, Integer> {

	Collection<Concierto> findAll();

}

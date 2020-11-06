package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import org.springframework.samples.petclinic.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Integer> {

	Collection<Festival> findAll();

}

package org.springframework.samples.springfest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Integer> {
	
	Collection<Concert> findAll();
	
	@Query("select c from Concert c where c.festival.id = ?1 ORDER BY c.horaCom ASC")
	Collection<Concert> findAllConcertsByFestivalId(int festivalId);

	@Query("select c from Concert c where c.recinto.id = ?1")
	Collection<Concert> findAllConciertosByRecintoId(int recintoId);

}

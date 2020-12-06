package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Integer> {

	Collection<Concert> findAll();
	
	@Query("select c from Concert c where c.festival.id = ?1 ORDER BY c.horaCom ASC")
	Collection<Concert> findAllConcertsByFestivalId(int festivalId);
	
//	@Query("select count(*) from Concert c where c.recinto.id = ?1 and (c.horaCom between ?2 and ?3 and c.horaFin between ?2 and ?3) or (c.horaFin > ?2) or (c.horaCom < ?3) ")
//	Integer findNumberConcertsByRecintoIdOnDate(int recintoId, LocalDateTime date1, LocalDateTime date2);
	
	@Query("select c from Concert c where c.recinto.id = ?1")
	Collection<Concert> findAllConciertosByRecintoId(int recintoId);
	

}

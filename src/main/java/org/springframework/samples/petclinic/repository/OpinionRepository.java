package org.springframework.samples.petclinic.repository;

import java.util.Collection; 

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Opinion;

public interface OpinionRepository extends CrudRepository<Opinion, Integer> {

    Collection<Opinion> findAll();

    @Query("select o from Opinion o where o.festival.id = ?1 ORDER BY o.fecha DESC")
    Collection<Opinion> findOpinionsByFestivalId(int festivalId);

    @Query("select AVG(o.puntuacion) from Opinion o where o.festival.id = ?1")
    Integer averageOpinionByFestivalId(int festivalId);

}
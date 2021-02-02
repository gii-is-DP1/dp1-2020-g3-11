package org.springframework.samples.springfest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}

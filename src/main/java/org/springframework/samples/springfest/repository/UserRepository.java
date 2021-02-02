package org.springframework.samples.springfest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.springfest.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}

package springfest.repository;

import org.springframework.data.repository.CrudRepository;

import springfest.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}

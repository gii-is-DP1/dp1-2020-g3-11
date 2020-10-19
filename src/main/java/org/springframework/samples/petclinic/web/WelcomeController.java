package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})

	  public String welcome(Map<String, Object> model) {	  
		  
		  List<Person> personas = new ArrayList<>();

	        Person fer = new Person();
	        fer.setFirstName("Fernando");
	        fer.setLastName(" Calvo");

	        Person javi = new Person();
	        javi.setFirstName("Javier");
	        javi.setLastName(" Rodriguez");

	        Person pablo = new Person();
	        pablo.setFirstName("Pablo");
	        pablo.setLastName(" Santos");

	        Person enrq = new Person();
	        enrq.setFirstName("Enrique");
	        enrq.setLastName(" Gonzalez");

	        Person ale = new Person();
	        ale.setFirstName("Alejandro");
	        ale.setLastName(" Manzano");

	        Person manu = new Person();
	        manu.setFirstName("Manuel");
	        manu.setLastName(" Bueno");

	        personas.add(pablo);
	        personas.add(ale);
	        personas.add(enrq);
	        personas.add(manu);
	        personas.add(fer);
	        personas.add(javi);

	        model.put("personas", personas);
	        model.put("title", "dp-2020");
	        model.put("Grupo", "G3-11");
      
	    return "welcome";
	  }
}

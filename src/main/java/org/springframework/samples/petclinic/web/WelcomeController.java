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

    @GetMapping({ "/", "/welcome" })
    public String welcome(Map<String, Object> model) {

        List<Person> persons = new ArrayList<>();

        Person fer = new Person();
        fer.setFirstName("Fernando");
        fer.setLastName(" Calvo");

        Person javi = new Person();
        javi.setFirstName("Javier");
        javi.setLastName(" Rodriguez");

        Person Pablo = new Person();
        Pablo.setFirstName("Pablo");
        Pablo.setLastName(" Santos");

        Person enrq = new Person();
        enrq.setFirstName("Enrique");
        enrq.setLastName(" González");

        Person ale = new Person();
        ale.setFirstName("Alejandro");
        ale.setLastName(" Manzano");

        Person manu = new Person();
        manu.setFirstName("Manuel");
        manu.setLastName(" Bueno");

        persons.add(Pablo);
        persons.add(ale);
        persons.add(enrq);
        persons.add(manu);
        persons.add(fer);
        persons.add(javi);

        model.put("persons", persons);
        model.put("title", "dp-2020");
        model.put("Grupo", "G3-11");

        return "welcome";
    }

}
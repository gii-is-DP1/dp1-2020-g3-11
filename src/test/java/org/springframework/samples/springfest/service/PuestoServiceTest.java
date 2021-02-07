package org.springframework.samples.springfest.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Puesto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PuestoServiceTest {
	
	@Autowired
	PuestoService puestoService;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	@Transactional
	public void shouldNotEditPuestoNegativePrecio() throws Exception {
		Puesto puesto = this.puestoService.findById(2).get();
		Integer precio = -43;

		assertThrows(Exception.class, () -> {
			puesto.setPrecio(precio);
			entityManager.flush();
			this.puestoService.save(puesto);
		});
	}
	
}

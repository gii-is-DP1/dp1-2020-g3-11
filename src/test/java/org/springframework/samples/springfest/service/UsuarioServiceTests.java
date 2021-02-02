package org.springframework.samples.springfest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.TipoUsuario;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UsuarioServiceTests {

	@Autowired
	protected UsuarioService usuarioService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void shouldFindAllUsuarios() {
		Collection<Usuario> listaUsuario = this.usuarioService.findAllUsuarios();
		assertThat(listaUsuario.size()).isEqualTo(10);
	}

	@Test
	void shouldFindTipoUsuarioWithCorrectName() throws Exception {
		TipoUsuario tipo = this.usuarioService.findTipoUsuario("Sponsor");
		assertThat(tipo.getName()).isEqualTo("Sponsor");
	}

	@Test
	void shouldFindUsuarioWithCorrectId() throws Exception {
		Usuario usuario20 = this.usuarioService.findUsuarioById(20);
		assertThat(usuario20.getFirstName()).isEqualTo("Manue");
		assertThat(usuario20.getTelefono()).isEqualTo("692811112");
	}

	@Test
	@Transactional
	void shouldInsertNewUsuario() throws Exception {
		Collection<Usuario> listaUsuario = this.usuarioService.findAllUsuarios();
		int tamaño = listaUsuario.size();
		TipoUsuario tipo = this.usuarioService.findTipoUsuario("Usuario");

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setTelefono("600000000");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		usuario.setDNI("35578899D");
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		usuario.setTipoUsuario(tipo);

		try {
			this.usuarioService.saveUsuario(usuario);
		} catch (Exception ex) {
			Logger.getLogger(UsuarioServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		listaUsuario = this.usuarioService.findAllUsuarios();
		assertThat(listaUsuario.size()).isEqualTo(tamaño + 1);
		assertThat(usuario.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldThrowExceptionInsertingNewArtistBlankParameter() throws Exception {
		TipoUsuario tipo = this.usuarioService.findTipoUsuario("Usuario");

		Usuario usuario = new Usuario();
		usuario.setFirstName("");
		usuario.setLastName("Rodriguez");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setTipoUsuario(tipo);
		usuario.setTelefono("689542122");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		usuario.setDNI("35578899D");
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);

		assertThrows(ConstraintViolationException.class, () -> {
			this.usuarioService.saveUsuario(usuario);
		});
	}

}

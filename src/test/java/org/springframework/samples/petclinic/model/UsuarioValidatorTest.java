package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class UsuarioValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenTelephoneEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setDNI("38694532Z");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setName("sponsor");
		usuario.setTipoUsuario(tipo);

		usuario.setTelefono("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

		ConstraintViolation<Usuario> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("telefono");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}

	@Test
	void shouldNotValidateWhenTipoUsuarioNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setDNI("38694532Z");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setName("sponsor");
		usuario.setTipoUsuario(null);

		usuario.setTelefono("639884123");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

		ConstraintViolation<Usuario> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipoUsuario");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldNotValidateWhenEmailEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("");
		usuario.setDNI("38694532Z");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setName("sponsor");
		usuario.setTipoUsuario(tipo);

		usuario.setTelefono("678999444");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

		ConstraintViolation<Usuario> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("correo");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}

	@Test
	void shouldNotValidateWhenDNIEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setTelefono("600000000");
		usuario.setFechaNacimiento(LocalDate.of(1999, 6, 22));
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setName("sponsor");
		usuario.setTipoUsuario(tipo);


		Validator validator = this.createValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

		ConstraintViolation<Usuario> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("DNI");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}

	@Test
	void shouldNotValidateWhenFechaNacimientoisNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Usuario usuario = new Usuario();
		usuario.setFirstName("Juanlu");
		usuario.setLastName("Munoz");
		usuario.setCorreo("juanlu@gmail.com");
		usuario.setTelefono("600000000");
		usuario.setDNI("00000000A");
		usuario.setFechaNacimiento(null);
		Set<Entrada> entradas = new HashSet<Entrada>();
		usuario.setEntradas(entradas);
		TipoUsuario tipo = new TipoUsuario();
		tipo.setName("sponsor");
		usuario.setTipoUsuario(tipo);


		Validator validator = this.createValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);

		ConstraintViolation<Usuario> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("fechaNacimiento");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

}

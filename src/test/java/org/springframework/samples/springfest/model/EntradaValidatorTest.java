package org.springframework.samples.springfest.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.springfest.model.Entrada;
import org.springframework.samples.springfest.model.EntradaType;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class EntradaValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenPrecioNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		
		Set<Usuario> users = new HashSet<Usuario>();
		Entrada entrada = new Entrada();
		entrada.setPrecio(null);
		entrada.setEntradaType(new EntradaType());
		entrada.setFestival(new Festival());

		entrada.setUsuario(users);

		
		Validator validator = createValidator();
		Set<ConstraintViolation<Entrada>> constraintViolations = validator.validate(entrada);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Entrada> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenEntradaTypeNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Set<Usuario> users = new HashSet<Usuario>();
		Entrada entrada = new Entrada();
		entrada.setPrecio(30);
		entrada.setEntradaType(null);
		entrada.setFestival(new Festival());

		entrada.setUsuario(users);

		
		Validator validator = createValidator();
		Set<ConstraintViolation<Entrada>> constraintViolations = validator.validate(entrada);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Entrada> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("entradaType");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	@Test
	void shouldNotValidateWhenPriceMinor0() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		Set<Usuario> users = new HashSet<Usuario>();
		Entrada entrada = new Entrada();
		entrada.setPrecio(-30);
		entrada.setEntradaType(new EntradaType());
		entrada.setFestival(new Festival());

		entrada.setUsuario(users);

		
		Validator validator = createValidator();
		Set<ConstraintViolation<Entrada>> constraintViolations = validator.validate(entrada);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Entrada> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("must be greater than 0");
	}
}

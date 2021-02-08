package org.springframework.samples.springfest.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class PuestoValidatorTest {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	void shouldNotValidateWhenPriceNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Puesto puesto = new Puesto();

		Recinto recinto = new Recinto();
		puesto.setRecinto(recinto);
		Festival festival = new Festival();
		puesto.setFestival(festival);
		TipoPuesto tipoPuesto = new TipoPuesto();
		puesto.setTipoPuesto(tipoPuesto);
		TipoTamaño tipoTamanio = new TipoTamaño();
		puesto.setTipoTamanio(tipoTamanio);
		

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Puesto>> constraintViolations = validator.validate(puesto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Puesto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}
	
	@Test
	void shouldNotValidateWhenPriceNegative() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Puesto puesto = new Puesto();
		puesto.setPrecio(-43);
		Recinto recinto = new Recinto();
		puesto.setRecinto(recinto);
		Festival festival = new Festival();
		puesto.setFestival(festival);
		TipoPuesto tipoPuesto = new TipoPuesto();
		puesto.setTipoPuesto(tipoPuesto);
		TipoTamaño tipoTamanio = new TipoTamaño();
		puesto.setTipoTamanio(tipoTamanio);
		

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Puesto>> constraintViolations = validator.validate(puesto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Puesto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
		assertThat(violation.getMessage()).isEqualTo("must be greater than 0");

	}
	
	@Test
	void shouldNotValidateWhenTipoPuestoNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Puesto puesto = new Puesto();
		puesto.setPrecio(30);
		Recinto recinto = new Recinto();
		puesto.setRecinto(recinto);
		Festival festival = new Festival();
		puesto.setFestival(festival);
		TipoTamaño tipoTamanio = new TipoTamaño();
		puesto.setTipoTamanio(tipoTamanio);
		

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Puesto>> constraintViolations = validator.validate(puesto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Puesto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipoPuesto");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}
	
	@Test
	void shouldNotValidateWhenTipoTamanioNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Puesto puesto = new Puesto();
		puesto.setPrecio(30);
		Recinto recinto = new Recinto();
		puesto.setRecinto(recinto);
		Festival festival = new Festival();
		puesto.setFestival(festival);
		TipoPuesto tipoPuesto = new TipoPuesto();
		puesto.setTipoPuesto(tipoPuesto);
		

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Puesto>> constraintViolations = validator.validate(puesto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Puesto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipoTamanio");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}
	
	@Test
	void shouldNotValidateWhenRecintoNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Puesto puesto = new Puesto();
		puesto.setPrecio(30);
		TipoTamaño tipoTamanio = new TipoTamaño();
		puesto.setTipoTamanio(tipoTamanio);
		Festival festival = new Festival();
		puesto.setFestival(festival);
		TipoPuesto tipoPuesto = new TipoPuesto();
		puesto.setTipoPuesto(tipoPuesto);
		

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Puesto>> constraintViolations = validator.validate(puesto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Puesto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("recinto");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}
	
}

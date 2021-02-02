package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfest.model.Concert;
import springfest.model.Festival;
import springfest.model.Puesto;
import springfest.model.Recinto;
import springfest.model.TipoRecinto;

public class RecintoValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Recinto recinto = new Recinto();

		recinto.setName("");
		Set<Concert> conciertos = new HashSet<Concert>();
		Set<Puesto> puestos = new HashSet<Puesto>();
		recinto.setConciertos(conciertos);
		recinto.setPuestos(puestos);
		Festival festival = new Festival();
		recinto.setFestival(festival);
		recinto.setHuecos(3);
		TipoRecinto tipoRecinto = new TipoRecinto();
		recinto.setTipoRecinto(tipoRecinto);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Recinto>> constraintViolations = validator.validate(recinto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Recinto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");

	}

	@Test
	void shouldNotValidateWhenTipoRecintoNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Recinto recinto = new Recinto();

		recinto.setName("Camping 3");
		Set<Concert> conciertos = new HashSet<Concert>();
		Set<Puesto> puestos = new HashSet<Puesto>();
		recinto.setConciertos(conciertos);
		recinto.setPuestos(puestos);
		Festival festival = new Festival();
		recinto.setFestival(festival);
		recinto.setHuecos(3);
		recinto.setTipoRecinto(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Recinto>> constraintViolations = validator.validate(recinto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Recinto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipoRecinto");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldNotValidateWhenNumHuecosNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Recinto recinto = new Recinto();

		recinto.setName("Camping 3");
		Set<Concert> conciertos = new HashSet<Concert>();
		Set<Puesto> puestos = new HashSet<Puesto>();
		recinto.setConciertos(conciertos);
		recinto.setPuestos(puestos);
		Festival festival = new Festival();
		recinto.setFestival(festival);
		recinto.setHuecos(null);
		TipoRecinto tipoRecinto = new TipoRecinto();
		recinto.setTipoRecinto(tipoRecinto);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Recinto>> constraintViolations = validator.validate(recinto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Recinto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("huecos");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldNotValidateWhenFestivalNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Recinto recinto = new Recinto();

		recinto.setName("Camping 3");
		Set<Concert> conciertos = new HashSet<Concert>();
		Set<Puesto> puestos = new HashSet<Puesto>();
		recinto.setConciertos(conciertos);
		recinto.setPuestos(puestos);
		recinto.setFestival(null);
		recinto.setHuecos(4);
		TipoRecinto tipoRecinto = new TipoRecinto();
		recinto.setTipoRecinto(tipoRecinto);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Recinto>> constraintViolations = validator.validate(recinto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Recinto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("festival");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

}
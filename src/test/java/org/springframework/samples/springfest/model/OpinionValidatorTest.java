package org.springframework.samples.springfest.model;

import static org.assertj.core.api.Assertions.assertThat; 

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class OpinionValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenDescripcionEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Opinion opinion = new Opinion();
		Festival festival = new Festival();
		opinion.setFestival(festival);
		opinion.setDescripcion("");
		opinion.setPuntuacion(3);
		opinion.setFecha(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
		Usuario ou = new Usuario();
		opinion.setOpinionUsuario(ou);
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Opinion>> constraintViolations = validator.validate(opinion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Opinion> violation = constraintViolations.stream().collect(Collectors.toList()).get(0);
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("size must be between 10 and 1024");
//		ConstraintViolation<Opinion> violation2 = constraintViolations.stream().collect(Collectors.toList()).get(1);
//		assertThat(violation2.getPropertyPath().toString()).isEqualTo("descripcion");
//		assertThat(violation2.getMessage()).isEqualTo("must not be blank");
	}

	@Test
	void shouldNotValidateWhenFestivalNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Opinion opinion = new Opinion();
		opinion.setFestival(null);
		opinion.setDescripcion("Me gusta mucho este festival le doy buena nota por su calidad y su arte.");
		opinion.setPuntuacion(3);;
		opinion.setFecha(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
		Usuario ou = new Usuario();
		opinion.setOpinionUsuario(ou);
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Opinion>> constraintViolations = validator.validate(opinion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Opinion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("festival");
		assertThat(violation.getMessage()).isEqualTo("must not be null");


	}

	@Test
	void shouldNotValidateWhenPuntuacionNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Opinion opinion = new Opinion();
		Festival festival = new Festival();
		opinion.setFestival(festival);
		opinion.setDescripcion("Me gusta mucho este festival le doy buena nota por su calidad y su arte.");
		opinion.setPuntuacion(null);;
		opinion.setFecha(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
		Usuario ou = new Usuario();
		opinion.setOpinionUsuario(ou);
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Opinion>> constraintViolations = validator.validate(opinion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Opinion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("puntuacion");
		assertThat(violation.getMessage()).isEqualTo("must not be null");


	}

	@Test
	void shouldNotValidateWhenFechaNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Opinion opinion = new Opinion();
		Festival festival = new Festival();
		opinion.setFestival(festival);
		opinion.setDescripcion("Me gusta mucho este festival le doy buena nota por su calidad y su arte.");
		opinion.setPuntuacion(3);;
		opinion.setFecha(null);
		Usuario ou = new Usuario();
		opinion.setOpinionUsuario(ou);
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Opinion>> constraintViolations = validator.validate(opinion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Opinion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("fecha");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}
	
	@Test
	void shouldNotValidateWhenUsuarioNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Opinion opinion = new Opinion();
		Festival festival = new Festival();
		opinion.setFestival(festival);
		opinion.setDescripcion("Me gusta mucho este festival le doy buena nota por su calidad y su arte.");
		opinion.setPuntuacion(3);;
		opinion.setFecha(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
		opinion.setOpinionUsuario(null);
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Opinion>> constraintViolations = validator.validate(opinion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Opinion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("opinionUsuario");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

}

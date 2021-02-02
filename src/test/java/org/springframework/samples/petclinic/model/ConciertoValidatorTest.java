package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import springfest.model.Artista;
import springfest.model.Concert;
import springfest.model.Festival;
import springfest.model.Recinto;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */


class ConciertoValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFechaNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Concert concierto = new Concert();
		concierto.setFecha(null);
		concierto.setArtista(new Artista());
		concierto.setHoraCom(LocalDateTime.of(2020, 12, 01, 12, 40));
		concierto.setHoraFin(LocalDateTime.of(2020, 12, 02, 12, 42));
		concierto.setRecinto(new Recinto());
		concierto.setFestival(new Festival());


		Validator validator = createValidator();
		Set<ConstraintViolation<Concert>> constraintViolations = validator.validate(concierto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Concert> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("fecha");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

}
	
	@Test
	void shouldNotValidateWhenArtistaNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Concert concierto = new Concert();
		concierto.setFecha(LocalDate.of(2020, 12, 01));
		concierto.setArtista(null);
		concierto.setHoraCom(LocalDateTime.of(2020, 12, 01, 12, 40));
		concierto.setHoraFin(LocalDateTime.of(2020, 12, 02, 12, 42));
		concierto.setRecinto(new Recinto());
		concierto.setFestival(new Festival());


		Validator validator = createValidator();
		Set<ConstraintViolation<Concert>> constraintViolations = validator.validate(concierto);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Concert> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("artista");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

}
}

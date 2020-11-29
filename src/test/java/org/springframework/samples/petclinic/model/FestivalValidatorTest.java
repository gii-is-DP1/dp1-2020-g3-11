package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean
 *         Validation)
 */
class FestivalValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Festival festival = new Festival();
		festival.setName("");
		festival.setAforoMax(50000);
		festival.setLocalizacion("Coria del Río");
		festival.setFechaCom(LocalDate.of(2020, 12, 20));
		festival.setFechaFin(LocalDate.of(2020, 12, 23));
		Validator validator = this.createValidator();

		Set<ConstraintViolation<Festival>> constraintViolations = validator.validate(festival);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Festival> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");

	}

	@Test
	void shouldNotValidateWhenLocalizacionEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Festival festival = new Festival();
		festival.setName("Coria Music");
		festival.setAforoMax(5000);
		festival.setLocalizacion("");
		festival.setFechaCom(LocalDate.of(2020, 12, 10));
		festival.setFechaFin(LocalDate.of(2020, 12, 13));

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Festival>> constraintViolations = validator.validate(festival);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Festival> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("localizacion");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}

	@Test
	void shouldNotValidateWhenFechaNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Festival festival = new Festival();
		festival.setName("Coria Music");
		festival.setAforoMax(5000);
		festival.setLocalizacion("Coria del Rio");
		festival.setFechaCom(null);
		festival.setFechaFin(LocalDate.of(2020, 12, 13));

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Festival>> constraintViolations = validator.validate(festival);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Festival> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("fechaCom");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldNotValidateWhenAforoNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Festival festival = new Festival();
		festival.setName("Coria Music");
		festival.setAforoMax(null);
		festival.setLocalizacion("Coria del Rio");
		festival.setFechaCom(LocalDate.of(2020, 12, 10));
		festival.setFechaFin(LocalDate.of(2020, 12, 13));

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Festival>> constraintViolations = validator.validate(festival);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Festival> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("aforoMax");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

}

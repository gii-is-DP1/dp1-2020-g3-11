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

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean
 *         Validation)
 */
class OfertaValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Oferta o = new Oferta();
		Festival festival = new Festival();
		TipoOferta to = new TipoOferta();
		Set<Entrada> e = new HashSet<Entrada>();
		o.setFestival(festival);
		o.setNombre("");
		o.setPrecioOferta(20);
		o.setTipoOferta(to);
		o.setEntradas(e);

		Validator validator = this.createValidator();

		Set<ConstraintViolation<Oferta>> constraintViolations = validator.validate(o);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Oferta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("nombre");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}


	@Test
	void shouldNotValidateWhenTipoOfertaNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Oferta o = new Oferta();
		Festival festival = new Festival();
		Set<Entrada> e = new HashSet<Entrada>();
		o.setFestival(festival);
		o.setNombre("Pack bebida");
		o.setPrecioOferta(20);
		;
		o.setTipoOferta(null);
		o.setEntradas(e);

		Validator validator = this.createValidator();

		Set<ConstraintViolation<Oferta>> constraintViolations = validator.validate(o);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Oferta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipoOferta");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldNotValidateWhenPrecioNull() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Oferta o = new Oferta();
		Festival festival = new Festival();
		TipoOferta to = new TipoOferta();
		Set<Entrada> e = new HashSet<Entrada>();
		o.setFestival(festival);
		o.setNombre("Cargador portatil");
		o.setPrecioOferta(null);
		;
		o.setTipoOferta(to);
		o.setEntradas(e);

		Validator validator = this.createValidator();

		Set<ConstraintViolation<Oferta>> constraintViolations = validator.validate(o);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Oferta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precioOferta");
		assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

}

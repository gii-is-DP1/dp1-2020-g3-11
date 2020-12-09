
package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.repository.ArtistaRepository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean
 *         Validation)
 */
class ArtistValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenTelephoneEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Artista artista = new Artista();
		artista.setName("Juanlu");
		artista.setCorreo("juanlu@gmail.com");
		GeneroType g = new GeneroType();
		artista.setGenero(g);
		artista.setTelefono("");
		Set<FestivalArtista> fa = new HashSet<FestivalArtista>();
		artista.setFestivales(fa);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Artista>> constraintViolations = validator.validate(artista);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Artista> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("telefono");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");

	}

}

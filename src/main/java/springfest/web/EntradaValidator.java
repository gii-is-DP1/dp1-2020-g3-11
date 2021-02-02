package springfest.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springfest.model.Entrada;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to
 * define such validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class EntradaValidator implements Validator {

	private static final String REQUIRED = "Campo requerido";

	@Override
	public void validate(Object obj, Errors errors) {
		Entrada entrada = (Entrada) obj;
		Integer precio = entrada.getPrecio();

		if (precio == null) {
			errors.rejectValue("precio", REQUIRED, REQUIRED);
		}

		if (precio == null) {
			errors.rejectValue("precio", REQUIRED, REQUIRED);

		} else {

			if (precio <= 0) {
				errors.rejectValue("precio", "El precio de la entrada debe ser mayor que 0",
						"El precio de la entrada debe ser mayor que 0");
			}
		}

		// type validation
		if (entrada.getEntradaType() == null) {
			errors.rejectValue("entradaType.name", "Debe elegir un tipo", "Debe elegir un tipo");
		}
	}

	/**
	 * This Validator validates *just* Entrada instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Entrada.class.isAssignableFrom(clazz);
	}
}
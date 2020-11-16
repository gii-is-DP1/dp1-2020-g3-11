package org.springframework.samples.petclinic.web;


import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Entrada entrada = (Entrada) obj;
		EntradaType tipo = entrada.getEntradaType();
		Integer precio = entrada.getPrecio();
		
		if (tipo != null || precio != null) {
			//validacion precio
			if(precio<0) {
				errors.rejectValue("precio", "El precio de la entrada debe ser mayor que 0",
						"El precio de la entrada debe ser mayor que 0");
			}
		}else {
			errors.rejectValue("precio", "Existe algún campo sin completar", "Existe algún campo sin completar");
			errors.rejectValue("tipo", "Existe algún campo sin completar", "Existe algún campo sin completar");
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

package org.springframework.samples.springfest.web;

import org.springframework.samples.springfest.model.Entrada;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

	@Override
	public boolean supports(Class<?> clazz) {
		return Entrada.class.isAssignableFrom(clazz);
	}
}
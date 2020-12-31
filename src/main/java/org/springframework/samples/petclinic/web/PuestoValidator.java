package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PuestoValidator implements Validator {

	private static final String REQUIRED = "Campo requerido";

	@Override
	public void validate(Object obj, Errors errors) {
		Puesto puesto = (Puesto) obj;
		Integer precio = puesto.getPrecio();

		if (precio == null) {
			errors.rejectValue("precio", REQUIRED, REQUIRED);
		}
//está doble
		if (precio == null) {
			errors.rejectValue("precio", REQUIRED, REQUIRED);

		} else {

			if (precio <= 0) {
				errors.rejectValue("precio", "El precio del puesto debe ser mayor que 0",
						"El precio del puesto debe ser mayor que 0");
			}
		}

		// type validation
		if (puesto.getTipoPuesto()== null) {
			errors.rejectValue("tipoPuesto.name", "Debe elegir un tipo", "Debe elegir un tipo");
		}
		if (puesto.getTipoTamanio()== null) {
			errors.rejectValue("tipoTamanio.name", "Debe elegir un tipo", "Debe elegir un tipo");
		}
		if (puesto.getRecinto()== null) {
			errors.rejectValue("recinto.name", "Debe elegir un tipo", "Debe elegir un tipo");
		}
	}

	/**
	 * This Validator validates *just* Puesto instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Puesto.class.isAssignableFrom(clazz);
	}
}
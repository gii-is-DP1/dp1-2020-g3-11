package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PuestoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Puesto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Puesto puesto = (Puesto) target;
		TipoPuesto tipoPuesto = puesto.getTipoPuesto();
		TipoTamaño tipoTamanio = puesto.getTipoTamanio();
		Recinto recinto = puesto.getRecinto();
		Integer precio = puesto.getPrecio();

		if (tipoPuesto == null) {
			errors.rejectValue("tipoPuesto.name", "Campo requerido", "Campo requerido");
		}

		if (tipoTamanio == null) {
			errors.rejectValue("tipoTamanio.name", "Campo requerido", "Campo requerido");
		}

		if (recinto == null) {
			errors.rejectValue("recinto.name", "Campo requerido", "Campo requerido");
		}
		
		if (precio == null) {
			errors.rejectValue("precio", "Campo requerido", "Campo requerido");
		} else {
			if (precio <= 0) {
				errors.rejectValue("precio", "El nº de puestos debe ser mayor que 0",
						"El nº puestos debe ser mayor que 0");
			}

		}

	}
}

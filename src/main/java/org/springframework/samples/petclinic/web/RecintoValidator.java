package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecintoValidator implements Validator {

	private static final String REQUIRED = "Campo requerido";

	@Override
	public void validate(Object target, Errors errors) {
		Recinto recinto = (Recinto) target;
		String name = recinto.getName();
		Integer huecos = recinto.getHuecos();
		TipoRecinto tipoRecinto = recinto.getTipoRecinto();
		Integer numEscenarios = recinto.getNumMaxEscenarios();

		if (recinto.isNew() && tipoRecinto == null)
			errors.rejectValue("name", "Escoge un tipo de recinto", "Escoge un tipo de recinto");

		if (name.isEmpty()) {
			errors.rejectValue("name", "Campo requerido", "Campo requerido");
		}

		if (huecos == null) {
			errors.rejectValue("huecos", REQUIRED, REQUIRED);
		} else {
			if (huecos <= 0) {
				errors.rejectValue("huecos", "El aforo debe ser mayor que 0", "El aforo debe ser mayor que 0");
			}
		}
		
		if(tipoRecinto != null && tipoRecinto.getName().equals("Escenario") && numEscenarios == null) {
			errors.rejectValue("numMaxEscenarios", REQUIRED, REQUIRED);
		}
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Recinto.class.isAssignableFrom(clazz);
	}

}

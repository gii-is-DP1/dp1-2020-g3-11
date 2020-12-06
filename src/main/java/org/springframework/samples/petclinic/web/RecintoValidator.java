package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.util.StringUtils;
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

		if (tipoRecinto == null)
			errors.rejectValue("name", "Escoge un tipo de recinto", "Escoge un tipo de recinto");

		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " Debe contener entre 3 y 50 caracteres",
					REQUIRED + " Debe contener entre 3 y 50 caracteres");
		}

		try {
            if (huecos == null) {
                errors.rejectValue("huecos", REQUIRED, REQUIRED);
            } else {
                if (huecos <= 0) {
                    errors.rejectValue("huecos", "El nº de puestos debe ser mayor que 0",
                            "El nº puestos debe ser mayor que 0");
                }
            }
        } catch (NumberFormatException n) {
            errors.rejectValue("huecos", "Este campo no puede contener caracteres",
                    "Este campo no puede contener caracteres");
        }

		if (tipoRecinto != null && tipoRecinto.getName().equals("Escenario") && numEscenarios == null) {
			errors.rejectValue("numMaxEscenarios", REQUIRED, REQUIRED);
		}

		if (tipoRecinto != null && tipoRecinto.getName().equals("Escenario") && numEscenarios <= 0) {
			errors.rejectValue("numMaxEscenarios", "El nº de escenarios debe ser mayor que 0",
					"El nº de escenarios debe ser mayor que 0");
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Recinto.class.isAssignableFrom(clazz);
	}

}

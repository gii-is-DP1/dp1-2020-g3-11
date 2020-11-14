package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RecintoValidator implements Validator {
	
	@Override
	public void validate(Object target, Errors errors) {
		Recinto recinto = (Recinto) target;
		String name = recinto.getName();
		Integer aforoMax = recinto.getAforoMaxRec();
		Integer huecos = recinto.getHuecos();
		TipoRecinto tipoRecinto = recinto.getTipoRecinto();
		
		if(name != null && aforoMax != null && huecos != null && tipoRecinto != null) {
			
			if(aforoMax == 0) {
				errors.rejectValue("aforoMaxRec", "El aforo debe ser mayor que 0",
						"El aforo debe ser mayor que 0");
			}
			
			if(huecos == 0) {
				errors.rejectValue("huecos", "El número de huecos para puestos debe ser mayor que 0",
						"El número de huecos para puestos debe ser mayor que 0");
			}
			
		} else {
			errors.rejectValue("name", "Existe algún campo sin completar", "Existe algún campo sin completar");
		}

	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Recinto.class.isAssignableFrom(clazz);
	}

}

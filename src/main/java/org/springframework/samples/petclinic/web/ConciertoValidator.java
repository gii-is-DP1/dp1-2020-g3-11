/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such
 * validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class ConciertoValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Concierto concierto = (Concierto) obj;
		LocalDate fecha = concierto.getFecha();
		LocalDateTime horaCom = concierto.getHoraCom();
		LocalDateTime horaFin = concierto.getHoraFin();

		if(fecha!=null || horaCom!=null || horaFin!=null) {
		// fecha validation
		if (fecha.isBefore(LocalDate.now())) {
			errors.rejectValue("fecha", "La fecha tiene que ser posterior a la actualidad", "La fecha tiene que ser posterior a la actualidad");
		}

		// horas validation
		if (horaFin.isBefore(horaCom)) {
					errors.rejectValue("horaCom", "La fecha comienzo tiene que ser anterior a la hora final", "La fecha comienzo tiene que ser anterior a la hora final");
				}
				
		// horas validation
		if (horaFin.isBefore(horaCom)) {
					errors.rejectValue("horaCom", "La fecha comienzo tiene que ser anterior a la hora final", "La fecha comienzo tiene que ser anterior a la hora final");
		}
		
		//hora de comienzo igual a la fecha de concierto
		
		if (!horaCom.toLocalDate().isEqual(fecha)) {
			errors.rejectValue("fecha", "La fecha del concierto debe ser igual a la fecha de inicio", "La fecha del concierto debe ser igual a la fecha de inicio");
			}
		
		} else {
			errors.rejectValue("fecha", "Existe algún campo sin completar", "Existe algún campo sin completar");
			errors.rejectValue("horaCom", "Existe algún campo sin completar", "Existe algún campo sin completar");
			errors.rejectValue("horaFin", "Existe algún campo sin completar", "Existe algún campo sin completar");

		}
		
		
	}

	/**
	 * This Validator validates *just* Concierto instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Concierto.class.isAssignableFrom(clazz);
	}

}

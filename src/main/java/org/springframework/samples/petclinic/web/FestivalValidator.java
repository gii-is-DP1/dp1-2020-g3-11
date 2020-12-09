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

import org.springframework.samples.petclinic.model.Festival;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FestivalValidator implements Validator {

	private static final String REQUIRED = "Campo requerido.";

	@Override
	public void validate(Object obj, Errors errors) {
		Festival festival = (Festival) obj;
		String name = festival.getName();
		String localizacion = festival.getLocalizacion();
		Integer aforoMax = festival.getAforoMax();
		LocalDate fechaCom = festival.getFechaCom();
		LocalDate fechaFin = festival.getFechaFin();

		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + "Debe contener entre 3 y 50 caracteres",
					REQUIRED + " Debe contener entre 3 y 50 caracteres");
		}

		if (!StringUtils.hasLength(localizacion)) {
			errors.rejectValue("localizacion", REQUIRED, REQUIRED);
		}

		if (aforoMax == null) {
			errors.rejectValue("aforoMax", REQUIRED, REQUIRED);
		} else {
			if (aforoMax <= 0) {
				errors.rejectValue("aforoMax", "El aforo máximo debe ser mayor que 0",
						"El aforo máximo debe ser mayor que 0");
			}
		}

		if (fechaCom == null) {
			errors.rejectValue("fechaCom", REQUIRED, REQUIRED);
		} else {
			if (fechaCom.isBefore(LocalDate.now())) {
				errors.rejectValue("fechaCom", "La fecha tiene que ser posterior a la actualidad",
						"La fecha tiene que ser posterior a la actualidad");
			}
		}

		if (fechaFin == null) {
			errors.rejectValue("fechaFin", REQUIRED, REQUIRED);

		} else {
			if (fechaFin.isBefore(fechaCom)) {

				errors.rejectValue("fechaFin",
						"El valor de la fecha fin no puede ser anterior a la fecha comienzo establecida",
						"El valor de la fecha fin no puede ser anterior a la fecha comienzo establecida");
			}
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Festival.class.isAssignableFrom(clazz);
	}

}

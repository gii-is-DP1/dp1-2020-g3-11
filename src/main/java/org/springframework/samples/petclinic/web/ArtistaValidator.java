
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

import org.springframework.samples.petclinic.model.Artista;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ArtistaValidator implements Validator {

	private static final String REQUIRED = "required";


	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Artista artista = (Artista) obj;
		String name = artista.getName();
		String correo = artista.getCorreo();
		String telefono = artista.getTelefono();

		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", REQUIRED + " and between 3 and 50 characters",
					REQUIRED + " and between 3 and 50 character");
		}

		// correo validation
		if (!StringUtils.hasLength(correo)) {
			errors.rejectValue("correo", REQUIRED, REQUIRED);
		}

		if (!correo.contains("@")) {
			errors.rejectValue("correo", " Your email must contain an @", " Your email must contain an @");
		}

		// telefono validation
		if (!StringUtils.hasLength(telefono)) {
			errors.rejectValue("telefono", REQUIRED, REQUIRED);
		}
		// tamaño
		if (!isNumeric(telefono)) {
			errors.rejectValue("telefono", " The phone must be a number", " The phone must be a number");
		}

		if (telefono.length() > 15 || telefono.length() < 9) {
			errors.rejectValue("telefono", REQUIRED + " and between 9 and 15 characters",
					REQUIRED + " and between 9 and 15 character");
		}

		// type validation
		if (artista.isNew() && artista.getGenero() == null) {
			errors.rejectValue("genero.name", "Debes seleccionar un género",
					"Debes seleccionar un género");
		}

	}

	/**
	 * This Validator validates *just* Concierto instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.isAssignableFrom(clazz);
	}

}
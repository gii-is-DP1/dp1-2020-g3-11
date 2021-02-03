package org.springframework.samples.springfest.web;

import org.springframework.samples.springfest.model.Artista;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ArtistaValidator implements Validator {

	private static final String REQUIRED = "Campo requerido.";

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
			errors.rejectValue("name", REQUIRED + " Debe contener entre 3 y 50 caracteres",
					REQUIRED + " Debe contener entre 3 y 50 caracteres");
		}

		// correo validation
		if (!StringUtils.hasLength(correo)) {
			errors.rejectValue("correo", REQUIRED, REQUIRED);
		}

		if (!correo.contains("@")) {
			errors.rejectValue("correo", " Tu email debe contener una @", "Tu email debe contener una @");
		}

		// telefono validation
		if (!StringUtils.hasLength(telefono)) {
			errors.rejectValue("telefono", REQUIRED, REQUIRED);
		}
		// tamaño
		if (!isNumeric(telefono)) {
			errors.rejectValue("telefono", " El teléfono debe ser númerico", " El teléfono debe ser númerico");
		}

		if (telefono.length() != 9) {
			errors.rejectValue("telefono", " Debe contener 9 digitos", "  Debe contener 9 digitos");
		} else {
			if (telefono.startsWith("-") || telefono.startsWith("+")) {
				errors.rejectValue("telefono", "El teléfono no puede contener simbolos",
						"El teléfono no puede contener simbolos");
			}
		}

		// type validation
		if (artista.isNew() && artista.getGenero() == null) {
			errors.rejectValue("genero.name", "Debes seleccionar un género", "Debes seleccionar un género");
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.isAssignableFrom(clazz);
	}

}

package org.springframework.samples.springfest.web;

import java.time.LocalDate;

import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UsuarioValidator implements Validator {

	private static final String REQUIRED = "Campo requerido";

	public UsuarioService usuarioService;

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
		Usuario usuario = (Usuario) obj;
		String nombre = usuario.getFirstName();
		String apellidos = usuario.getLastName();
		String correo = usuario.getCorreo();
		String telefono = usuario.getTelefono();
		String nombreUsuario = usuario.getUser().getUsername();
		String contraseña = usuario.getUser().getPassword();
		String dni = usuario.getDNI();
		LocalDate fechaNac = usuario.getFechaNacimiento();

		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length() > 50) {
			errors.rejectValue("firstName", REQUIRED + " Debe contener entre 1 y 50 caracteres",
					REQUIRED + " Debe contener entre 1 y 50 caracteres");
		}

		// apellidos validation
		if (!StringUtils.hasLength(apellidos) || apellidos.length() > 50) {
			errors.rejectValue("lastName", REQUIRED + " Debe contener entre 1 y 50 caracteres",
					REQUIRED + " Debe contener entre 1 y 50 caracteres");
		}

		// CORREO
		if (!StringUtils.hasLength(correo)) {
			errors.rejectValue("correo", REQUIRED, REQUIRED);
		}
		if (!correo.contains("@")) {
			errors.rejectValue("correo", " Tu email debe contener una @", "Tu email debe contener una @");
		}
		// DNI
		if ((dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false || !isNumeric(dni.substring(0, 7)))) {
			errors.rejectValue("DNI", "El DNI debe estar formado por 8 números y una letra",
					"El DNI debe estar formado por 8 números y una letra");
		}

		// TELEFONO
		if (!StringUtils.hasLength(telefono)) {
			errors.rejectValue("telefono", REQUIRED, REQUIRED);
		}

		if (!isNumeric(telefono)) {
			errors.rejectValue("telefono", " El teléfono debe ser númerico", " El teléfono debe ser númerico");
		}

		if (telefono.length() > 15 || telefono.length() < 9) {
			errors.rejectValue("telefono", REQUIRED + " Debe contener entre 9 y 15 caracteres",
					REQUIRED + " Debe contener entre 9 y 15 caracteres");
		} else {
			if (telefono.startsWith("-") || telefono.startsWith("+")) {
				errors.rejectValue("telefono", "El teléfono no puede contener simbolos",
						"El teléfono no puede contener simbolos");
			}
		}

		// TIPO DE USUARIO
		if (usuario.isNew() && usuario.getTipoUsuario() == null) {
			errors.rejectValue("tipoUsuario", "Debes seleccionar un tipo de usuario",
					"Debes seleccionar un tipo de usuario");
		}

		// FECHA DE NACIMIENTO
		if (fechaNac == null) {
			errors.rejectValue("fechaNacimiento", REQUIRED, REQUIRED);
		} else {
			if (fechaNac.isAfter(LocalDate.now())) {
				errors.rejectValue("fechaNacimiento", "La fecha de nacimiento debe ser anterior a la actual",
						"La fecha de nacimiento debe ser anterior a la actual");
			}
		}
		// USUARIO
		if (!StringUtils.hasLength(nombreUsuario)) {
			errors.rejectValue("user.username", REQUIRED, REQUIRED);
		
		}

		// CONTRASEÑA
		if (!StringUtils.hasLength(contraseña) || contraseña.length() < 7) {
			errors.rejectValue("user.password", REQUIRED + " y con longitud superior a 7 caracteres",
					REQUIRED + " y con longitud superior a 7 caracteres");
		}

		// MARCA
		if (usuario.getTipoUsuario() != null && usuario.getTipoUsuario().getName().equals("Sponsor")
				&& !StringUtils.hasLength(usuario.getMarca())) {
			errors.rejectValue("marca", REQUIRED + " si eres sponsor", REQUIRED + " si eres sponsor");
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

}

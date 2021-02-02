package org.springframework.samples.springfest.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.springfest.model.TipoUsuario;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.AuthoritiesService;
import org.springframework.samples.springfest.service.UserService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

	private final UsuarioService usuarioService;

	private static final String VIEWS_USUARIO_CREATE_FORM = "usuarios/createOrUpdateUsuarioForm";

	@Autowired
	public UsuarioController(UsuarioService clinicService, UserService userService,
			AuthoritiesService authoritiesService) {
		this.usuarioService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("usuario")
	public void initUsuariotBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new UsuarioValidator());
	}

	@GetMapping(value = "/usuarios/new")
	public String initCreationForm(ModelMap model) {
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);

		return VIEWS_USUARIO_CREATE_FORM;
	}

	@PostMapping(value = "/usuarios/new")
	public String processCreationForm(@Valid Usuario usuario, BindingResult result) {
		if (usuarioService.checkCorreoExists(usuario.getCorreo())) {
			result.addError(new FieldError("usuario", "correo", "El correo ya existe"));
		}
		if (usuarioService.checkUsuarioExists(usuario.getUser().getUsername())) {
			result.addError(new FieldError("usuario", "user.username", "El nombre de usuario ya está en uso"));
		}
		if (usuarioService.checkDNIExists(usuario.getDNI())) {
			result.addError(new FieldError("usuario", "DNI", "El DNI ya está registrado"));
		}
		if (usuarioService.checkTelefonoExists(usuario.getTelefono())) {
			result.addError(new FieldError("usuario", "telefono", "El Telefono ya está registrado"));
		}

		if (result.hasErrors()) {
			return VIEWS_USUARIO_CREATE_FORM;
		} else {
			TipoUsuario tipo = usuarioService.findTipoUsuario((usuario.getTipoUsuario().getName()));
			usuario.setTipoUsuario(tipo);

			this.usuarioService.saveUsuario(usuario);
			return "redirect:/";
		}
	}
}

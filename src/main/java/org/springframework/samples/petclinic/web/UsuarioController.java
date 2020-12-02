package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoUsuario;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

	private final UsuarioService usuarioService;

	private static final String VIEWS_USUARIO_CREATE_FORM = "usuarios/createUsuarioForm";

	@Autowired
	public UsuarioController(UsuarioService clinicService, UserService userService, AuthoritiesService authoritiesService) {
		this.usuarioService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("usuario")
	public void initArtistBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RegistroUsuarioValidator());
	}

	@GetMapping(value = "/usuarios/new")
	public String initCreationForm(Map<String, Object> model) {
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		return VIEWS_USUARIO_CREATE_FORM;
	}

	@PostMapping(value = "/usuarios/new")
	public String processCreationForm(@Valid Usuario usuario, BindingResult result) {
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

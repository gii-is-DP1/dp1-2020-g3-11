package org.springframework.samples.springfest.web;

import java.security.Principal;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoRecinto;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.RecintoService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mifestival/recintos")
public class RecintoController {

	public static final String RECINTOS_FORM = "recintos/createOrUpdateRecintoForm";
	public static final String RECINTOS_DETALLES_FORM = "recintos/detallesRecinto";
	public static final String RECINTOS_LISTING = "recintos/recintoListing";

	@Autowired
	FestivalService festivalService;

	@Autowired
	RecintoService recintoService;

	@Autowired
	UsuarioService usuarioService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@Autowired
	public RecintoController(FestivalService festivalService, RecintoService recintoService) {
		this.festivalService = festivalService;
		this.recintoService = recintoService;
	}

	@GetMapping
	public String listRecintos(ModelMap model) {

		model.addAttribute("recintos", recintoService.findAll());
		return RECINTOS_LISTING;
	}

	@ModelAttribute("tipos_recinto")
	public Collection<TipoRecinto> populateRecintoTypes() {
		return this.recintoService.findRecintoTypes();
	}

	@InitBinder("recinto")
	public void initRecintoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RecintoValidator());
	}

	@GetMapping("/{id}/edit")
	public String editRecinto(@PathVariable("id") int id, ModelMap model) {
		Recinto recinto = recintoService.findById(id);

		model.addAttribute("recinto", recinto);
		return RECINTOS_FORM;

	}

	@PostMapping("/{id}/edit")
	public String editRecinto(@PathVariable("id") int id, Principal principal, @Valid Recinto modifiedRecinto,
			BindingResult binding, @RequestParam(value = "version", required = false) Integer version, ModelMap model) {
		Recinto recinto = recintoService.findById(id);
		if (binding.hasErrors()) {
			return RECINTOS_FORM;
		} else {
			Recinto recintoBD = this.recintoService.findRecintoById(id);
			if (recintoBD.getVersion() != version) {
				model.put("message", "Modificación concurrente del recinto, inténtelo más tarde por favor.");
				return RECINTOS_FORM;
			}
			BeanUtils.copyProperties(modifiedRecinto, recinto, "id");
			TipoRecinto tipoRecinto = this.recintoService.findRecintoType(modifiedRecinto.getTipoRecinto().getName());
			modifiedRecinto.setTipoRecinto(tipoRecinto);
			modifiedRecinto.setFestival(
					this.festivalService.findFestivalById(usuarioLogueado(principal).getFestival().getId()).get());
			modifiedRecinto.incrementVersion();
			this.recintoService.save(modifiedRecinto);
			model.addAttribute("message", "Recinto actualizado correctamente!");
			return "redirect:/mifestival";
		}
	}

	@GetMapping("/{id}/detalles")
	public String mostrarDetallesRecinto(ModelMap model, @PathVariable("id") int recintoId) {
		model.addAttribute("recinto", this.recintoService.findById(recintoId));
		model.addAttribute("conciertos", this.recintoService.findAllConciertosById(recintoId));
		model.addAttribute("puestos", this.recintoService.findAllPuestosById(recintoId));
		return RECINTOS_DETALLES_FORM;
	}

	@GetMapping("/new")
	public String editNewRecinto(ModelMap model) {
		model.addAttribute("recinto", new Recinto());
		return RECINTOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewRecinto(Principal principal, @Valid Recinto recinto, BindingResult binding, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (binding.hasErrors()) {
			return RECINTOS_FORM;
		} else {
			TipoRecinto tipo = recintoService.findRecintoType(recinto.getTipoRecinto().getName());
			recinto.setFestival(festivalService.findFestivalById2(festivalId));
			recinto.setTipoRecinto(tipo);
			recintoService.save(recinto);
			return "redirect:/mifestival";
		}
	}
}

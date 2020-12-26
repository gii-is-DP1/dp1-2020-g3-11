package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.PuestoService;
import org.springframework.samples.petclinic.service.RecintoService;
import org.springframework.samples.petclinic.service.UsuarioService;
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

@Controller
public class PuestoController {

	public static final String PUESTOS_FORM = "puestos/createOrUpdatePuestoForm";
	public static final String PUESTOS_LISTING = "puestos/puestosListing";

	@Autowired
	FestivalService festivalService;

	@Autowired
	PuestoService puestoService;

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
	public PuestoController(PuestoService puestoService, FestivalService festivalService) {
		this.puestoService = puestoService;
		this.festivalService = festivalService;
	}

	@ModelAttribute("festival")
	public Festival findFestival(Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return this.festivalService.findFestivalById(festivalId).get();
	}
	
	@GetMapping("/festivales/{festivalId}/puestos")
	public String listPuestosSponsor(ModelMap model, @PathVariable("festivalId") int festivalId) {

		model.addAttribute("puestos", puestoService.findAllPuestosByFestivalId(festivalId));
		return PUESTOS_LISTING;
	}

//
//	@InitBinder("festival")
//	public void initFestivalBinder(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}

	@GetMapping("/mifestival/puestos")
	public String listPuestosAdmin(Principal principal, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		model.addAttribute("puestos", puestoService.findAllPuestosByFestivalId(festivalId));
		return PUESTOS_LISTING;
	}

	@InitBinder("puesto")
	public void initRecintoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PuestoValidator());
	}

	@ModelAttribute("tipos_puesto")
	public Collection<TipoPuesto> populateTipoPuesto() {
		return this.puestoService.findTiposPuesto();
	}

	@ModelAttribute("tipos_tamaño")
	public Collection<TipoTamaño> populateTipoTamaño() {
		return this.puestoService.findTiposTamaño();
	}

	@ModelAttribute("recintos")
	public List<String> puestos(Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return recintoService.findAllRecintosByFestivalId(festivalId).stream().map(r -> r.getName())
				.collect(Collectors.toList());
	}

	@GetMapping("/mifestival/puestos/new")
	public String initCreationNewPuesto(ModelMap model) {
		Puesto puesto = new Puesto();
		model.put("puesto", puesto);
		return PUESTOS_FORM;
	}

	@PostMapping("/mifestival/puestos/new")
	public String processCreationNewPuesto(Principal principal, @Valid Puesto puesto, BindingResult binding,
			ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (binding.hasErrors()) {
			return PUESTOS_FORM;
		} else {
			TipoPuesto tipoPuesto = puestoService.findPuestoType(puesto.getTipoPuesto().getName());
			TipoTamaño tipoTamanio = puestoService.findTamañoType(puesto.getTipoTamanio().getName());
			Recinto recinto = recintoService.findRecintoByName(puesto.getRecinto().getName());
			puesto.setFestival(festivalService.findFestivalById(festivalId).get());
			puesto.setTipoPuesto(tipoPuesto);
			puesto.setTipoTamanio(tipoTamanio);
			puesto.setRecinto(recinto);
			puestoService.save(puesto);
			model.addAttribute("message", "Puesto creado correctamente!");
			return "redirect:/mifestival/puestos";
		}
	}

	@GetMapping("/mifestival/puestos/{id}/delete")
	public String deletePuesto(@PathVariable("id") int id, Principal principal, ModelMap model) {
		if (this.puestoService.findById(id).isPresent()) {
			Optional<Puesto> puesto = puestoService.findById(id);
			puestoService.delete(puesto.get());
			model.addAttribute("message", "Puesto borrado correctamente!");
			return "redirect:/mifestival/puestos";
		} else {
			model.addAttribute("message", "No se encuentra!");
			return "redirect:/mifestival/puestos";
		}

	}

	@GetMapping("/mifestival/puestos/{id}/edit")
	public String initUpdatePuesto(@PathVariable("id") int id, ModelMap model) {
		Puesto puesto = puestoService.findById(id).orElse(null);
		model.put("puesto", puesto);
		return PUESTOS_FORM;
	}

	@PostMapping("/mifestival/puestos/{id}/edit")
	public String processUpdatePuesto(@PathVariable("id") int id, Principal principal, @Valid Puesto puesto,
			BindingResult binding, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();
		Puesto modifiedPuesto = puestoService.findById(id).orElse(null);

		if (binding.hasErrors()) {
			model.put("puesto", puesto);
			return PUESTOS_FORM;

		} else {
			BeanUtils.copyProperties(puesto, modifiedPuesto, "id");
			modifiedPuesto.setTipoPuesto(this.puestoService.findPuestoType(puesto.getTipoPuesto().getName()));
			modifiedPuesto.setTipoTamanio(this.puestoService.findTamañoType(puesto.getTipoTamanio().getName()));
			modifiedPuesto.setFestival(festivalService.findFestivalById(festivalId).get());
			modifiedPuesto.setRecinto(recintoService.findRecintoByName(puesto.getRecinto().getName()));
			this.puestoService.save(modifiedPuesto);
			model.addAttribute("message", "Puesto actualizado correctamente!");
			return "redirect:/mifestival/puestos";

		}

	}

}

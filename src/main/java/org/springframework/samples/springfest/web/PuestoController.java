package org.springframework.samples.springfest.web;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Puesto;
import org.springframework.samples.springfest.model.Recinto;
import org.springframework.samples.springfest.model.TipoPuesto;
import org.springframework.samples.springfest.model.TipoTamaño;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.PuestoService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PuestoController {

	public static final String PUESTOS_FORM = "puestos/createOrUpdatePuestoForm";
	public static final String PUESTOS_LISTING = "puestos/puestosListing";
	public static final String ALQUILAR_PUESTOS = "puestos/alquilarPuestos";
	
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

	@InitBinder("puesto")
	public void initPuestoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PuestoValidator());
	}
<<<<<<< HEAD

=======
>>>>>>> branch 'fercaldurS4_2' of https://github.com/gii-is-DP1/dp1-2020-g3-11.git
	
	@GetMapping("/mifestival/puestos")
	public String listPuestos(Principal principal, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		model.addAttribute("puestos", puestoService.findAllPuestosByFestivalId(festivalId));
		return PUESTOS_LISTING;
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
	public List<String> recintos(Principal principal) {

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
			log.info("Creando nuevo puesto");
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
			BindingResult binding, @RequestParam(value = "version", required = false) Integer version, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();
		Puesto modifiedPuesto = puestoService.findById(id).orElse(null);

		if (binding.hasErrors()) {
			model.put("puesto", puesto);
			return PUESTOS_FORM;

		} else {
			Puesto puestoBD = this.puestoService.findById(id).get();
			if(puestoBD.getVersion() != version) {
				model.put("message", "Modificación concurrente del puesto, inténtelo más tarde por favor.");
				return PUESTOS_FORM;
			}
			BeanUtils.copyProperties(puesto, modifiedPuesto, "id");
			modifiedPuesto.setTipoPuesto(this.puestoService.findPuestoType(puesto.getTipoPuesto().getName()));
			modifiedPuesto.setTipoTamanio(this.puestoService.findTamañoType(puesto.getTipoTamanio().getName()));
			modifiedPuesto.setFestival(festivalService.findFestivalById(festivalId).get());
			modifiedPuesto.setRecinto(recintoService.findRecintoByName(puesto.getRecinto().getName()));
			modifiedPuesto.incrementVersion();
			this.puestoService.save(modifiedPuesto);
			model.addAttribute("message", "Puesto actualizado correctamente!");
			return "redirect:/mifestival/puestos";

		}

	}

}

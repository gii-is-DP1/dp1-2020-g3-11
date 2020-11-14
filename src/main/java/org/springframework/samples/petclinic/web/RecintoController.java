package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.samples.petclinic.service.RecintoService;
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
@RequestMapping("/recintos")
public class RecintoController {

	public static final String RECINTOS_NEW_FORM = "recintos/createRecintoForm";
	public static final String RECINTOS_EDIT_FORM = "recintos/updateRecintoForm";
	public static final String RECINTOS_LISTING = "recintos/recintoListing";

	@Autowired
	RecintoService recintoService;

	@GetMapping
	public String listRecintos(ModelMap model) {

		model.addAttribute("recintos", recintoService.findAll());
		return RECINTOS_LISTING;
	}
	
	@InitBinder("recinto")
	public void initRecintoBinder(WebDataBinder dataBinder) {
	dataBinder.setValidator(new RecintoValidator());
	}
	
	@ModelAttribute("tipos_recinto")
	public Collection<TipoRecinto> populateRecintoTypes() {
		return this.recintoService.findRecintoTypes();
	}

	@GetMapping("/{id}/edit")
	public String editRecinto(@PathVariable("id") int id, ModelMap model) {
		Optional<Recinto> recinto = recintoService.findById(id);
		if (recinto.isPresent()) {
			model.addAttribute("recinto", recinto.get());
			return RECINTOS_EDIT_FORM;
		} else {
			model.addAttribute("message", "No podemos encontrar el recinto que intentas editar!");
			return listRecintos(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editRecinto(@PathVariable("id") int id, @Valid Recinto modifiedRecinto, BindingResult binding,
			ModelMap model) {
		Optional<Recinto> recinto = recintoService.findById(id);
		if (binding.hasErrors()) {
			return RECINTOS_EDIT_FORM;
		} else {
			BeanUtils.copyProperties(modifiedRecinto, recinto.get(), "id");
			recintoService.save(recinto.get());
			model.addAttribute("message", "Recinto actualizado correctamente!");
			return listRecintos(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteService(@PathVariable("id") int id, ModelMap model) {
		Optional<Recinto> recinto = recintoService.findById(id);
		if (recinto.isPresent()) {
			recintoService.delete(recinto.get());
			model.addAttribute("message", "El festival se borró correctamente!");
			return listRecintos(model);
		} else {
			model.addAttribute("message", "No podemos encontrar el festival que intentas borar!");
			return listRecintos(model);
		}
	}

	@GetMapping("/new")
	public String editNewRecinto(ModelMap model) {
		model.addAttribute("recinto", new Recinto());
		return RECINTOS_NEW_FORM;
	}

	@PostMapping("/new")
	public String saveNewRecinto(@Valid Recinto recinto, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return RECINTOS_NEW_FORM;
		} else {
			TipoRecinto tipo = this.recintoService.findRecintoType(recinto.getTipoRecinto().getName());
			recinto.setTipoRecinto(tipo);
			this.recintoService.save(recinto);
			model.addAttribute("message", "El recinto se creó correctamente!");
			return listRecintos(model);
		}
	}
}

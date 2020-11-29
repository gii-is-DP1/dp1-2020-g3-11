package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.TipoRecinto;
import org.springframework.samples.petclinic.service.FestivalService;
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
@RequestMapping("festivales/{festivalId}/recintos")
public class RecintoController {

	public static final String RECINTOS_FORM = "recintos/createOrUpdateRecintoForm";
	public static final String RECINTOS_DETALLES_FORM = "recintos/detallesRecintoForm";
	public static final String RECINTOS_LISTING = "recintos/recintoListing";

	@Autowired
	FestivalService festivalService;

	@Autowired
	RecintoService recintoService;

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
	public String editRecinto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId, ModelMap model) {
		Optional<Recinto> recinto = recintoService.findById(id);
		if (recinto.isPresent()) {
			model.addAttribute("recinto", recinto.get());
			return RECINTOS_FORM;
		} else {
			model.addAttribute("message", "No podemos encontrar el recinto que intentas editar!");
			return listRecintos(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editRecinto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId,
			@Valid Recinto modifiedRecinto, BindingResult binding, ModelMap model) {
		Optional<Recinto> recinto = recintoService.findById(id);
		if (binding.hasErrors()) {
			return RECINTOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedRecinto, recinto.get(), "id");
			TipoRecinto tipoRecinto = this.recintoService.findRecintoType(modifiedRecinto.getTipoRecinto().getName());
			modifiedRecinto.setTipoRecinto(tipoRecinto);
			modifiedRecinto.setFestival(this.festivalService.findFestivalById(festivalId).get());
			this.recintoService.save(modifiedRecinto);
			model.addAttribute("message", "Recinto actualizado correctamente!");
			return "redirect:/festivales/{festivalId}";
		}
	}

//	@GetMapping("/{id}/delete")
//	public String deleteService(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId,
//			ModelMap model) {
//		Optional<Recinto> recinto = recintoService.findById(id);
//		if (recinto.isPresent()) {
//			recintoService.delete(recinto.get());
//			model.addAttribute("message", "El recinto se borró correctamente!");
//			return "redirect:/festivales/{festivalId}";
//		} else {
//			model.addAttribute("message", "No podemos encontrar el recinto que intentas borar!");
//			return "redirect:/festivales/{festivalId}";
//		}
//	}

	@GetMapping("/{id}/detalles_recinto")
	public String mostrarDetallesRecinto(ModelMap model, @PathVariable("id") int recintoId) {
		model.addAttribute("recinto", this.recintoService.findById(recintoId).get());
		model.addAttribute("conciertos", this.recintoService.findAllConciertosById(recintoId));
		return RECINTOS_DETALLES_FORM;
	}
	
	@GetMapping("/new")
	public String editNewRecinto(ModelMap model) {
		model.addAttribute("recinto", new Recinto());
		return RECINTOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewRecinto(@PathVariable("festivalId") int festivalId, @Valid Recinto recinto,
			BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
//			model.addAttribute("recinto", recinto);
			return RECINTOS_FORM;
		} else {
			TipoRecinto tipo = recintoService.findRecintoType(recinto.getTipoRecinto().getName());
			recinto.setFestival(festivalService.findFestivalById(festivalId).get());
			recinto.setTipoRecinto(tipo);
			recintoService.save(recinto);
			return "redirect:/festivales/{festivalId}";
		}
	}
}

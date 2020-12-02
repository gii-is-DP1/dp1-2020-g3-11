package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.PuestoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/festivales/{festivalId}/puestos")
public class PuestoController {
	
	public static final String PUESTOS_FORM = "puestos/createOrUpdatePuestoForm";
	public static final String PUESTOS_LISTING = "puestos/puestosListing";
	
	@Autowired
	FestivalService festivalService;

	@Autowired
	PuestoService puestoService;

	@GetMapping
	public String listPuestos(@PathVariable("festivalId") int festivalId, ModelMap model) {

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
	
	@GetMapping("/new")
	public String createNewPuesto(ModelMap model) {
		model.addAttribute("puesto", new Puesto());
		return PUESTOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewPuesto(@PathVariable("festivalId") int festivalId, @Valid Puesto puesto,
			BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return PUESTOS_FORM;
		} else {
			TipoPuesto tipoPuesto = puestoService.findPuestoType(puesto.getTipoPuesto().getName());
			TipoTamaño tipoTamanio = puestoService.findTamañoType(puesto.getTipoTamaño().getName());
			puesto.setTipoPuesto(tipoPuesto);
			puesto.setTipoTamaño(tipoTamanio);
			puesto.setFestival(festivalService.findFestivalById(festivalId).get());
			puestoService.save(puesto);
			return "redirect:/festivales/{festivalId}";
		}
	}
	
}

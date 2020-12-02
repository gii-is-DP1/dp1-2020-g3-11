package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.TipoPuesto;
import org.springframework.samples.petclinic.model.TipoTamaño;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.PuestoService;
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
@RequestMapping("/festivales/{festivalId}/puestos")
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
	public PuestoController(PuestoService puestoService, FestivalService festivalService) {
		this.puestoService = puestoService;
		this.festivalService = festivalService;
	}

	@ModelAttribute("festival")
	public Optional<Festival> findFestival(@PathVariable("festivalId") int festivalId) {
		return this.festivalService.findFestivalById(festivalId);
	}

	@InitBinder("festival")
	public void initFestivalBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

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
	public String initCreationNewPuesto(ModelMap model) {
		Puesto puesto = new Puesto();
		model.put("puesto", puesto);
		return PUESTOS_FORM;
	}

	@PostMapping("/new")
	public String processCreationNewPuesto(@PathVariable("festivalId") int festivalId, @Valid Puesto puesto,
			BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			return PUESTOS_FORM;
		} else {
			TipoPuesto tipoPuesto = puestoService.findPuestoType(puesto.getTipoPuesto().getName());
			TipoTamaño tipoTamanio = puestoService.findTamañoType(puesto.getTipoTamanio().getName());
			puesto.setFestival(festivalService.findFestivalById(festivalId).get());
			puesto.setTipoPuesto(tipoPuesto);
			puesto.setTipoTamanio(tipoTamanio);
//			puesto.setRecinto(this.recintoService.findRecintoByName(puesto.getRecinto().getName()));
			puestoService.save(puesto);
			return "redirect:/festivales/{festivalId}/puestos";
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePuesto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId, ModelMap model) {
		Optional<Puesto> puesto = puestoService.findById(id);
		puestoService.delete(puesto.get());
		return "redirect:/festivales/{festivalId}/puestos";
	}

	@GetMapping("/{id}/edit")
	public String initUpdatePuesto(@PathVariable("id") int id, ModelMap model) {
		Puesto puesto = puestoService.findById(id).orElse(null);
		model.put("puesto", puesto);
		return PUESTOS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String processUpdatePuesto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId,
			@Valid Puesto puesto, BindingResult binding, ModelMap model){
	
		if (binding.hasErrors()) {
			model.put("puesto", puesto);
			return PUESTOS_FORM;

		} else {
			Puesto modifiedPuesto = puestoService.findById(id).orElse(null);
			BeanUtils.copyProperties(puesto, modifiedPuesto, "id", "festival");
			TipoPuesto tipoPuesto = puestoService.findPuestoType(modifiedPuesto.getTipoPuesto().getName());
			TipoTamaño tipoTamanio = puestoService.findTamañoType(modifiedPuesto.getTipoTamanio().getName());
			modifiedPuesto.setFestival(festivalService.findFestivalById(festivalId).get());
			modifiedPuesto.setTipoPuesto(tipoPuesto);
			modifiedPuesto.setTipoTamanio(tipoTamanio);
			puestoService.save(modifiedPuesto);
			return "redirect:/festivales/{festivalId}/puestos";

		}

	}

}
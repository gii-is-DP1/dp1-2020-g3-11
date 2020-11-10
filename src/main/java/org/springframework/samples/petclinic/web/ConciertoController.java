package org.springframework.samples.petclinic.web;

import java.util.Optional; 

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.samples.petclinic.service.ConciertoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conciertos")
public class ConciertoController {

	public static final String CONCIERTOS_FORM = "conciertos/createOrUpdateConciertoForm";
	public static final String CONCIERTOS_LISTING = "conciertos/conciertoListing";

	@Autowired
	ConciertoService conciertoService;
	

	@GetMapping
	public String listConciertos(ModelMap model) {

		model.addAttribute("conciertos", conciertoService.findAll());
		return CONCIERTOS_LISTING;
	}
	
	

	@InitBinder("concierto")
	public void initConciertoBinder(WebDataBinder dataBinder) {
	dataBinder.setValidator(new ConciertoValidator());
	}

	@GetMapping("/{id}/edit")
	public String editConcierto(@PathVariable("id") int id, ModelMap model) {
		Optional<Concierto> concierto = conciertoService.findById(id);
		if (concierto.isPresent()) {
			model.addAttribute("concierto", concierto.get());
			return CONCIERTOS_FORM;
		} else {
			model.addAttribute("message", "We cannot find the concierto you tried to edit!");
			return listConciertos(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editConcierto(@PathVariable("id") int id, @Valid Concierto modifiedconcierto, BindingResult binding,
			ModelMap model) {
		Optional<Concierto> concierto = conciertoService.findById(id);
		if (binding.hasErrors()) {
			return CONCIERTOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedconcierto, concierto.get(), "id");
			conciertoService.save(concierto.get());
			model.addAttribute("message", "concierto updated succesfully!");
			return listConciertos(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteConcierto(@PathVariable("id") int id, ModelMap model) {
		Optional<Concierto> concierto = conciertoService.findById(id);
		if (concierto.isPresent()) {
			conciertoService.delete(concierto.get());
			model.addAttribute("message", "The concierto was deleted successfully!");
			return listConciertos(model);
		} else {
			model.addAttribute("message", "We cannot find the concierto you tried to delete!");
			return listConciertos(model);
		}
	}

	@GetMapping("/new")
	public String editNewconcierto(ModelMap model) {
		model.addAttribute("concierto", new Concierto());
		return CONCIERTOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewConcierto(@Valid Concierto concierto, BindingResult binding, ModelMap model) {
		
		if (binding.hasErrors()) {
			return CONCIERTOS_FORM;
		
		}else{
			
			conciertoService.save(concierto);
			model.addAttribute("message", "The concierto was created successfully!");
			return listConciertos(model);
		}
	}
	
}

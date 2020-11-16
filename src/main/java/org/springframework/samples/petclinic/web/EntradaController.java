package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional; 

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;
import org.springframework.samples.petclinic.service.EntradaService;
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
@RequestMapping("/entradas")
public class EntradaController {
	public static final String ENTRADAS_FORM = "entradas/createOrUpdateEntradaForm";
	public static final String ENTRADAS_LISTING = "entradas/entradaListing";
	
	@Autowired
	EntradaService entradaService;
	
	
	@GetMapping
	public String listEntradas(ModelMap model) {

		model.addAttribute("entradas", entradaService.findAll());
		return ENTRADAS_LISTING;
	}
	
	@ModelAttribute("entradatype")
    public Collection<String> entradaTypes() {
        return entradaService.findEntradaTypes();

    }
	

//	@InitBinder("entrada")
//	public void initEntradaBinder(WebDataBinder dataBinder) {
//	dataBinder.setValidator(new EntradaValidator());
//	}

	@GetMapping("/{id}/edit")
	public String editEntrada(@PathVariable("id") int id, ModelMap model) {
		Optional<Entrada> entrada = entradaService.findById(id);
		if (entrada.isPresent()) {
			model.addAttribute("entrada", entrada.get());
			return ENTRADAS_FORM;
		} else {
			model.addAttribute("message", "We cannot find the entrada you tried to edit!");
			return "redirect:/entradas";
		}
	}

	@PostMapping("/{id}/edit")
	public String editEntrada(@PathVariable("id") int id, @Valid Entrada modifiedentrada, BindingResult binding,
			ModelMap model) {
		Optional<Entrada> entrada = entradaService.findById(id);
		if (binding.hasErrors()) {
			return ENTRADAS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedentrada, entrada.get(), "id");
			EntradaType entradatype = this.entradaService.findEntradaType(modifiedentrada.getEntradaType().getName());
			modifiedentrada.setEntradaType(entradatype);
			entradaService.save(modifiedentrada);
			model.addAttribute("message", "entrada updated succesfully!");
			return "redirect:/entradas";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteEntrada(@PathVariable("id") int id, ModelMap model) {
		Optional<Entrada> entrada = entradaService.findById(id);
		if (entrada.isPresent()) {
			entradaService.delete(entrada.get());
			model.addAttribute("message", "The entrada was deleted successfully!");
			return "redirect:/entradas";
		} else {
			model.addAttribute("message", "We cannot find the entrada you tried to delete!");
			return "redirect:/entradas";
		}
	}

	@GetMapping("/new")
	public String createNewentrada(ModelMap model) {
		model.addAttribute("entrada", new Entrada());
		return ENTRADAS_FORM;
	}

	@PostMapping("/new")
	public String saveNewEntrada(@Valid Entrada entrada, BindingResult binding, ModelMap model) {
		
		if (binding.hasErrors()) {
			return ENTRADAS_FORM;
		
		}else{
			EntradaType entradatype = this.entradaService.findEntradaType(entrada.getEntradaType().getName());
            entrada.setEntradaType(entradatype);
			entradaService.save(entrada);
			model.addAttribute("message", "The entrada was created successfully!");
			return "redirect:/entradas";
		}
	}
}

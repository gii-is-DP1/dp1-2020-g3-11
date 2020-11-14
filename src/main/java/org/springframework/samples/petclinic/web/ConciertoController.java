package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.ConciertoService;
import org.springframework.samples.petclinic.service.FestivalArtistaService;
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
@RequestMapping("festivales/{festivalId}/conciertos")
public class ConciertoController {

	public static final String CONCIERTOS_FORM = "conciertos/createOrUpdateConciertoForm";
	public static final String CONCIERTOS_LISTING = "conciertos/conciertoListing";

	@Autowired
	ConciertoService conciertoService;
	RecintoService recintoService;
	FestivalService festivalService;
	ArtistaService artistaService;
	FestivalArtistaService festivalArtistaService;
	
	@Autowired
	public ConciertoController(ConciertoService conciertoService, FestivalService festivalService, ArtistaService artistaService, 
			RecintoService recintoService, FestivalArtistaService festivalArtistaService) {
		this.conciertoService = conciertoService;
        this.festivalService = festivalService;
        this.recintoService = recintoService;
        this.artistaService = artistaService;
        this.festivalArtistaService = festivalArtistaService;
	}

	@ModelAttribute("festival")
	public Optional<Festival> findFestival(@PathVariable("festivalId") int festivalId) {
		return this.festivalService.findById(festivalId);
	}
	
	@ModelAttribute("artistas")
	public Collection<String> artistas(@PathVariable("festivalId") int festivalId) {
		return festivalArtistaService.findAllArtistasByFestivalId(festivalId).stream().map(x-> x.getName()).collect(Collectors.toList());

	}
	
	@ModelAttribute("recintos")
	public Collection<String> recintos(@PathVariable("festivalId") int festivalId) {
		return conciertoService.findAllRecintosByFestivalId(festivalId).stream().map(x-> x.getName()).collect(Collectors.toList());
	}
	
	@GetMapping
	public String listConciertos(@PathVariable("festivalId") int festivalId, ModelMap model) {

		model.addAttribute("conciertos", conciertoService.findAllConciertosByFestivalId(festivalId));
		return CONCIERTOS_LISTING;
	}

	@InitBinder("concierto")
	public void initConciertoBinder(WebDataBinder dataBinder) {
	dataBinder.setValidator(new ConciertoValidator());
	}

	@GetMapping("/{id}/edit")
	public String initEditConcierto(@PathVariable("id") int id, ModelMap model) {
		Concierto concierto = conciertoService.findById(id).orElse(null);
		model.put("concierto", concierto);
		return CONCIERTOS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editConcierto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId, @Valid Concierto concierto, BindingResult binding,
			ModelMap model) {
		if (binding.hasErrors()) {
			model.put("concierto", concierto);
			return CONCIERTOS_FORM;
		} else {
			Festival festival = festivalService.findById(festivalId).orElse(null);
			Concierto modifiedConcierto = conciertoService.findById(id).orElse(null);
			BeanUtils.copyProperties(concierto, modifiedConcierto, "id", "festival");
			Artista artista = this.conciertoService.findArtistaByName(modifiedConcierto.getArtista().getName());
			Recinto recinto = this.conciertoService.findRecintoByName(modifiedConcierto.getRecinto().getName());
			modifiedConcierto.setArtista(artista);
			modifiedConcierto.setRecinto(recinto);
			conciertoService.save(modifiedConcierto);
			model.addAttribute("message", "concierto updated succesfully!");
			return "redirect:/festivales/{festivalId}/conciertos";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteConcierto(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId, ModelMap model) {
		Optional<Concierto> concierto = conciertoService.findById(id);
		if (concierto.isPresent()) {
			conciertoService.delete(concierto.get());
			model.addAttribute("message", "The concierto was deleted successfully!");
			return "redirect:/festivales/{festivalId}/conciertos";
		} else {
			model.addAttribute("message", "We cannot find the concierto you tried to delete!");
			return "redirect:/festivales/{festivalId}/conciertos";
		}
	}

	@GetMapping("/new")
	public String editNewconcierto(ModelMap model) {
		model.addAttribute("concierto", new Concierto());
		return CONCIERTOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewConcierto(@Valid Concierto concierto, @Valid Festival festival, BindingResult binding, ModelMap model) {
		
		if (binding.hasErrors()) {
			return CONCIERTOS_FORM;
		
		}else{
			
			concierto.setRecinto(conciertoService.findRecintoByName(concierto.getRecinto().getName()));
			concierto.setArtista(conciertoService.findArtistaByName(concierto.getArtista().getName()));
			concierto.setFestival(festival);
			conciertoService.save(concierto);
			model.addAttribute("message", "The concierto was created successfully!");
			return "redirect:/festivales/{festivalId}/conciertos";


		}
	}
	
}

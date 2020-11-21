package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Concert;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.ConcertService;
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
public class ConcertController {

	public static final String CONCERTS_FORM = "concerts/createOrUpdateConcertForm";
	public static final String CONCERTS_LISTING = "concerts/concertListing";

	@Autowired
	ConcertService concertService;
	@Autowired
	RecintoService recintoService;
	@Autowired
	FestivalService festivalService;
	@Autowired
	ArtistaService artistService;
	@Autowired
	FestivalArtistaService festivalArtistService;

	@Autowired
	public ConcertController(ConcertService concertService, FestivalService festivalService,
			ArtistaService artistService, RecintoService recintoService,
			FestivalArtistaService festivalArtistService) {
		this.concertService = concertService;
		this.festivalService = festivalService;
		this.recintoService = recintoService;
		this.artistService = artistService;
		this.festivalArtistService = festivalArtistService;
	}

	@ModelAttribute("festival")
	public Optional<Festival> findFestival(@PathVariable("festivalId") int festivalId) {
		return this.festivalService.findFestivalById(festivalId);
	}

	@ModelAttribute("artistas")
	public Collection<String> artistas(@PathVariable("festivalId") int festivalId) {
		return festivalArtistService.findAllArtistasByFestivalId(festivalId).stream().map(x -> x.getName())
				.collect(Collectors.toList());

	}

	@ModelAttribute("recintos")
	public Collection<String> recintos(@PathVariable("festivalId") int festivalId) {
		return recintoService.findAllRecintosByFestivalId(festivalId).stream()
				.filter(x-> x.getTipoRecinto().getName().equals("Escenario"))
				.map(x -> x.getName())
				.collect(Collectors.toList());
	}
	
	@InitBinder("festival")
	public void initFestivalBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	

	@InitBinder("concert")
	public void initConcertBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ConcertValidator());
	}

	@GetMapping
	public String listConcerts(@PathVariable("festivalId") int festivalId, ModelMap model) {

		model.addAttribute("concerts", concertService.findAllConcertsByFestivalId(festivalId));
		return CONCERTS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String initUpdateConcert(@PathVariable("id") int id, ModelMap model) {
		Concert concert = concertService.findById(id).orElse(null);
		model.put("concert", concert);
		return CONCERTS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String processUpdateConcert(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId,
			@Valid Concert concert, BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			model.put("concert", concert);
			return CONCERTS_FORM;

		} else {
			Concert modifiedConcert = concertService.findById(id).orElse(null);
			BeanUtils.copyProperties(concert, modifiedConcert, "id", "festival");
			Artista artista = this.artistService.findArtistaByName(modifiedConcert.getArtista().getName());
			Recinto recinto = this.recintoService.findRecintoByName(modifiedConcert.getRecinto().getName());

			modifiedConcert.setArtista(artista);
			modifiedConcert.setRecinto(recinto);
			concertService.save(modifiedConcert);
		}
		return "redirect:/festivales/{festivalId}/conciertos";

	}

	@GetMapping("/{id}/delete")
	public String deleteConcert(@PathVariable("id") int id, @PathVariable("festivalId") int festivalId,
			ModelMap model) {
		Optional<Concert> concert = concertService.findById(id);
		if (concert.isPresent()) {
			concertService.delete(concert.get());
			model.addAttribute("message", "The concert was deleted successfully!");
			return "redirect:/festivales/{festivalId}/conciertos";
		} else {
			model.addAttribute("message", "We cannot find the concert you tried to delete!");
			return "redirect:/festivales/{festivalId}/conciertos";
		}
	}

	@GetMapping("/new")
	public String initCreationConcert(ModelMap model) {
		Concert concert= new Concert();
		model.put("concert", concert);
		return CONCERTS_FORM;
	}

	@PostMapping("/new")
	public String processCreationConcert(@PathVariable("festivalId") int idFestival, @Valid Concert concert, BindingResult binding,
			ModelMap model) {

		if (binding.hasErrors()) {
//			binding.getFieldErrors().forEach(x-> binding.rejectValue(x.getField(), x.getDefaultMessage(), x.getDefaultMessage()));
			return CONCERTS_FORM;
		} else {
			concert.setRecinto(this.recintoService.findRecintoByName(concert.getRecinto().getName()));
			concert.setArtista(this.artistService.findArtistaByName(concert.getArtista().getName()));
			concert.setFestival(this.festivalService.findFestivalById(idFestival).get());
			this.concertService.save(concert);

		}
		return "redirect:/festivales/{festivalId}/conciertos";

	}

}

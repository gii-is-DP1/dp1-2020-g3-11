package org.springframework.samples.petclinic.web;

import java.util.Collection ;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.FestivalArtista;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.FestivalArtistaService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/festivales")
public class FestivalController {

	public static final String FESTIVALES_FORM = "festivales/createOrUpdateFestivalForm";
	public static final String FESTIVALES_LISTING = "festivales/festivalListing";
	public static final String FESTIVALES_DETAILS = "festivales/festivalDetails";
	public static final String ARTISTAS_LISTA = "festivales/listArtistasAñadir";

	@Autowired
	FestivalService festivalService;

	@Autowired
	ArtistaService artistaService;
	
	@Autowired
	FestivalArtistaService festivalArtistaService;

	@GetMapping
	public String listFestivales(ModelMap model) {
		model.addAttribute("festivales", festivalService.findAll());
		return FESTIVALES_LISTING;
	}

	@GetMapping("/{festivalId}")
	public String showFestival(ModelMap model, @PathVariable("festivalId") int festivalId) {
		Festival festival = festivalService.findById(festivalId).orElse(null);
		if (festival != null) {
			model.addAttribute("festival", festival);
			model.addAttribute("artistas", festivalArtistaService.findAllArtistasByFestivalId(festivalId));
		} else {
			return "redirect:/oups";
		}
		return FESTIVALES_DETAILS;
	}

	@GetMapping(value = "/{festivalId}/artistas/listdisponibles")
	public String listaArtistasDisponibles(@PathVariable("festivalId") int festivalId, ModelMap model) {
		Festival festival = festivalService.findById(festivalId).orElse(null);
		Collection<Artista> artistas = artistaService.findAll();
		artistas.removeAll(festivalArtistaService.findAllArtistasByFestivalId(festivalId));
		model.addAttribute("artistasDisponibles", artistas);
		model.addAttribute("festival", festival);
		return ARTISTAS_LISTA;
	}
	
//	@GetMapping(value = "/{festivalId}/conciertos")
//	public String listaConciertos(@PathVariable("festivalId") int festivalId, ModelMap model) {
//		Festival festival = festivalService.findById(festivalId).orElse(null);
//		if (festival != null) {
//		Collection<Concierto> conciertos = festivalConciertoService.findAllConciertosByFestivalId(festivalId);
//		model.addAttribute("conciertos", conciertos);
//		model.addAttribute("festival", festival);
//		} else {
//			return "redirect:/oups";
//		}
//		return CONCIERTOS_LISTA;
//	}

	@GetMapping(value = "/{festivalId}/artistas/{artistaId}/add")
	public String asociarArtistaFestival(@PathVariable("festivalId") int festivalId,
			@PathVariable("artistaId") int artistaId) {
		if (!festivalArtistaService.existByArtistaIdFestivalId(festivalId, artistaId)) {
			Festival festival = festivalService.findById(festivalId).orElse(null);
			Artista artista = artistaService.findArtistaById(artistaId).orElse(null);
			FestivalArtista fa = new FestivalArtista();
			fa.setArtista(artista);
			fa.setFestival(festival);
			festivalArtistaService.save(fa);
		}
		return "redirect:/festivales/" + festivalId;
	}

	@GetMapping("/{id}/edit")
	public String editFestival(@PathVariable("id") int id, ModelMap model) {
		Optional<Festival> festival = festivalService.findById(id);
		if (festival.isPresent()) {
			model.addAttribute("festival", festival.get());
			return FESTIVALES_FORM;
		} else {
			model.addAttribute("message", "We cannot find the festival you tried to edit!");
			return listFestivales(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editFestival(@PathVariable("id") int id, @Valid Festival modifiedFestival, BindingResult binding,
			ModelMap model) {
		Optional<Festival> festival = festivalService.findById(id);
		if (binding.hasErrors()) {
			return FESTIVALES_FORM;
		} else {
			BeanUtils.copyProperties(modifiedFestival, festival.get(), "id");
			festivalService.save(festival.get());
			model.addAttribute("message", "Festival updated succesfully!");
			return listFestivales(model);
		}
	}

	@GetMapping("/{festivalId}/delete")
	public String deleteFestival(@PathVariable("festivalId") int festivalId, ModelMap model) {
		Optional<Festival> festival = festivalService.findById(festivalId);
		if (festival.isPresent()) {
			festivalService.delete(festival.get());
			model.addAttribute("message", "The Festival was deleted successfully!");
			return listFestivales(model);
		} else {
			model.addAttribute("message", "We cannot find the Festival you tried to delete!");
			return listFestivales(model);
		}
	}

	@GetMapping("/{festivalId}/artistas/{artistaId}/delete")
	public String deleteArtistaDeFestival(@PathVariable("festivalId") int festivalId,
			@PathVariable("artistaId") int artistaId, ModelMap model) {
		FestivalArtista fa = festivalArtistaService.findByArtistaIdFestivalId(festivalId, artistaId);
		if (fa != null) {
			festivalArtistaService.delete(fa);
			model.addAttribute("message", "The Artist was deleted successfully!");
			return showFestival(model, festivalId);
		} else {
			model.addAttribute("message", "We cannot find the Artist you tried to delete!");
			return showFestival(model, festivalId);
		}
	}

	@GetMapping("/new")
	public String editNewFestival(ModelMap model) {
		model.addAttribute("festival", new Festival());
		return FESTIVALES_FORM;
	}

	@PostMapping("/new")
	public String saveNewFestival(@Valid Festival festival, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return FESTIVALES_FORM;
		} else {
			festivalService.save(festival);
			model.addAttribute("message", "The Festival was created successfully!");
			return listFestivales(model);
		}
	}

}

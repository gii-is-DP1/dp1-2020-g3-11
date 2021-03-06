package org.springframework.samples.springfest.web;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.springfest.model.Artista;
import org.springframework.samples.springfest.model.GeneroType;
import org.springframework.samples.springfest.service.ArtistaService;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArtistaController {

	public static final String ARTISTAS_FORM = "artistas/createOrUpdateArtistaForm";
	public static final String ARTISTAS_LISTING = "artistas/artistasListing";



	@Autowired
	ArtistaService artistaService;

	@Autowired
	FestivalService festivalService;
	
	@GetMapping("/artistas")
	public String findAll(@RequestParam(name = "page", defaultValue = "0") int page, ModelMap model) {
		
		log.info("Accediendo al listado de artistas");

		PageRequest pageRequest = PageRequest.of(page, 4);
		
		Page<Artista> pageArtista = artistaService.getAll(pageRequest);
		
		int totalPage = pageArtista.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(0, totalPage-1).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("list", pageArtista.getContent());
		
		return ARTISTAS_LISTING;
		
	}

	@ModelAttribute("generos")
	public Collection<String> populateGeneroTypes() {
		return this.artistaService.findGeneroTypes();
	}

	@Autowired
	public ArtistaController(FestivalService festivalService, ArtistaService artistaService) {
		this.festivalService = festivalService;
		this.artistaService = artistaService;
	}

	@InitBinder("artista")
	public void initArtistBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ArtistaValidator());
	}

	@GetMapping(value = "/artistas/new")
	public String initCreationForm(ModelMap model) {
		Artista artista = new Artista();
		model.put("artista", artista);
		return ARTISTAS_FORM;
	}

	@PostMapping(value = "/artistas/new")
	public String processCreationForm(@Valid Artista artista, BindingResult result, ModelMap model) {
		if (artistaService.checkCorreoExists(artista.getCorreo())) {
			result.addError(new FieldError("artista", "correo", "El correo ya existe"));
		}
		if (artistaService.checkTelefonoExists(artista.getTelefono())) {
			result.addError(new FieldError("artista", "telefono", "El telefono ya existe"));
		}
		if (artistaService.checkNombreExists(artista.getName())) {
			result.addError(new FieldError("artista", "name", "El nombre ya existe"));
		}
		if (result.hasErrors()) {
			return ARTISTAS_FORM;
		} else {
			GeneroType genero = this.artistaService.findGeneroType(artista.getGenero().getName());
			artista.setGenero(genero);
			this.artistaService.save(artista);
		}
		return "redirect:/artistas";
	}

	@GetMapping(value = "/artistas/{artistaId}/edit")
	public String initUpdateForm(@PathVariable("artistaId") int artistaId, ModelMap model) {
		Artista artista = this.artistaService.findArtistaById(artistaId);
		model.put("artista", artista);
		return ARTISTAS_FORM;
	}

	@PostMapping(value = "/artistas/{artistaId}/edit")
	public String processUpdateForm(@Valid Artista artista, BindingResult result, @PathVariable("artistaId") int artistaId,
			@RequestParam(value = "version", required = false) Integer version, ModelMap model) {
		if (result.hasErrors()) {
			model.put("artista", artista);
			return ARTISTAS_FORM;
		} else {
			Artista artistaBD = this.artistaService.findArtistaById(artistaId);
			if (artistaBD.getVersion() != version) {
				model.put("message", "Modificación concurrente del artista, inténtelo más tarde por favor.");
				return ARTISTAS_FORM;
			}
			Artista artistaToUpdate = this.artistaService.findArtistaById(artistaId);
			BeanUtils.copyProperties(artista, artistaToUpdate, "id", "festival");
			GeneroType genero = this.artistaService.findGeneroType(artistaToUpdate.getGenero().getName());
			artistaToUpdate.setGenero(genero);
			artistaToUpdate.incrementVersion();
			this.artistaService.save(artistaToUpdate);
		}
		return "redirect:/artistas";

	}

}

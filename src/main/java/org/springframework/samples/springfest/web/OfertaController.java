package org.springframework.samples.springfest.web;

import java.security.Principal; 
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OfertaService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mifestival/ofertas")
public class OfertaController {

	public static final String OFERTAS_FORM = "ofertas/createOrUpdateOfertaForm";
	public static final String OFERTAS_LISTING = "ofertas/ofertaListing";

	@Autowired
	FestivalService festivalService;
	
	@Autowired
	OfertaService ofertaService;

	@Autowired
	UsuarioService usuarioService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@Autowired
	public OfertaController(FestivalService festivalService, OfertaService ofertaService) {
		this.festivalService = festivalService;
		this.ofertaService = ofertaService;
	}

	@ModelAttribute("festival")
	public Festival findFestival(Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return this.festivalService.findFestivalById(festivalId).get();
	}


	@InitBinder("oferta")
	public void initOfertaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new OfertaValidator());
	}

	@GetMapping
	public String listaOfertas(Principal principal, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		model.addAttribute("ofertas", ofertaService.findAllOfertasByFestivalId(festivalId));
		return OFERTAS_LISTING;
	}
	
	@ModelAttribute("tiposOferta")
	public Collection<String> entradaTypes() {
		return ofertaService.findTiposOfertas();

	}

	@GetMapping("/new")
	public String initCreationOferta(ModelMap model) {
		Oferta oferta = new Oferta();
		model.put("oferta", oferta);
		return OFERTAS_FORM;
	}

	@PostMapping("/new")
	public String processCreationOferta(Principal principal, @Valid Oferta oferta, BindingResult binding,
			ModelMap model) throws DataAccessException {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (binding.hasErrors()) {
			return OFERTAS_FORM;
		} else {
			
			TipoOferta tipoOferta = ofertaService.findTipoOfertaByName(oferta.getTipoOferta().getName());
			oferta.setFestival(this.festivalService.findFestivalById(festivalId).get());
			oferta.setTipoOferta(tipoOferta);
			ofertaService.save(oferta);
			model.addAttribute("message", "La oferta fue creada correctamente!");
		}

		return "redirect:/mifestival/ofertas";

	}
}

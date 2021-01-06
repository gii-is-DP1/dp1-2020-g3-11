package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.EntradaService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.samples.petclinic.service.exceptions.AlcoholOfertAgeException;
import org.springframework.samples.petclinic.service.exceptions.OpinionNotAllowedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntradaController {
	public static final String ENTRADAS_FORM = "entradas/createOrUpdateEntradaForm";
	public static final String ENTRADAS_LISTING = "entradas/entradaListing";

	@Autowired
	FestivalService festivalService;

	@Autowired
	EntradaService entradaService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	OfertaService ofertaService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@GetMapping
	public String listEntradas(ModelMap model) {

		model.addAttribute("entradas", entradaService.findAll());
		return ENTRADAS_LISTING;
	}

	@GetMapping("/festivales/{festivalId}/entradas/{entradaId}/comprar")
	public String comprarEntrada(ModelMap model, @PathVariable("festivalId") int festivalId,
			@PathVariable("entradaId") int entradaId, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Entrada entrada = entradaService.findById(entradaId).orElse(null);

		List<Oferta> ofertas = ofertaService.findAllOfertasByFestivalId(festivalId).stream()
				.collect(Collectors.toList());

		model.addAttribute("datosUsuario", usuario);
		model.addAttribute("datosFestival", festival);
		model.addAttribute("datosEntrada", entrada);

		List<Oferta> ofertasDisp = ofertas;

		for (int i = 0; i < ofertasDisp.size(); i++) {

			if (entrada.getOfertas().contains(ofertasDisp.get(i))) {

				ofertasDisp.removeAll(entrada.getOfertas());

			}

		}

		model.addAttribute("datosOferta", ofertasDisp);

		Integer precio = entrada.getPrecio();

		Integer precioOfertas = entrada.getOfertas().stream().mapToInt(o -> o.getPrecioOferta()).sum();

		Integer precioTotal = precio + precioOfertas;

		model.addAttribute("precioTotal", precioTotal);

		return "entradas/entradaComprada";
	}

	@GetMapping(value = "/festivales/{festivalId}/entradas/{entradaId}/asociar/{ofertaId}")
	public String asociarOfertaEntrada(ModelMap model, @PathVariable("ofertaId") int ofertaId,
			@PathVariable("festivalId") int festivalId, @PathVariable("entradaId") int entradaId, Principal principal, BindingResult binding) throws DataAccessException, AlcoholOfertAgeException {

		try {
		Entrada entrada = entradaService.findById(entradaId).orElse(null);
		Oferta o = ofertaService.findById(ofertaId);
		entrada.getOfertas().add(o);
		o.getEntradas().add(entrada);

		entradaService.save(entrada);
		ofertaService.save(o);

		return "redirect:/festivales/{festivalId}/entradas/{entradaId}/comprar";
		
	} catch (AlcoholOfertAgeException e) {
		binding.rejectValue("tipoOferta", "Debes ser mayor de edad para escoger esta oferta.",
				"Debes ser mayor de edad para escoger esta oferta.");
		return "redirect:/festivales/{festivalId}/entradas/{entradaId}/asociar/{ofertaId}";

	}
	}

	@GetMapping(value = "/festivales/{festivalId}/entradas/{entradaId}/quitar/{ofertaId}")
	public String quitarOfertaEntrada(ModelMap model, @PathVariable("ofertaId") int ofertaId,
			@PathVariable("festivalId") int festivalId, @PathVariable("entradaId") int entradaId, Principal principal) throws DataAccessException, AlcoholOfertAgeException {

		Entrada entrada = entradaService.findById(entradaId).orElse(null);
		Oferta o = ofertaService.findById(ofertaId);
		entrada.getOfertas().remove(o);
		o.getEntradas().remove(entrada);

		entradaService.save(entrada);
		ofertaService.save(o);

		return "redirect:/festivales/{festivalId}/entradas/{entradaId}/comprar";
	}

	@GetMapping("/festivales/{festivalId}/entradas/{entradaId}/gracias")
	public String graciasPorComprarEntrada(ModelMap model, @PathVariable("festivalId") int festivalId,
			@PathVariable("entradaId") int entradaId, Principal principal) throws DataAccessException, AlcoholOfertAgeException {

		Usuario usuario = usuarioLogueado(principal);
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Entrada entrada = entradaService.findById(entradaId).orElse(null);
		entrada.getUsuario().add(usuario);
		usuario.getEntradas().add(entrada);
		entradaService.save(entrada);

		festivalService.reducirEntradasRestantes(festival);
		festivalService.save(festival);

		model.addAttribute("datosUsuario", usuario);
		model.addAttribute("datosFestival", festival);
		model.addAttribute("datosEntrada", entrada);

		return "entradas/vistaGraciasPorTuCompra";
	}

	@GetMapping("festivales/{festivalId}/entradas")
	public String listEntradasUsuario(ModelMap model, @PathVariable("festivalId") int festivalId) {

		model.addAttribute("entradas", entradaService.findAllEntradasByFestivalId(festivalId));
		return ENTRADAS_LISTING;
	}

	@GetMapping("/misEntradas")
	public String listEntradasCompradasUsuario(ModelMap model, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);

		model.addAttribute("entradas", entradaService.findEntradasCompradasUsuario(usuario.getId()));
		return "entradas/misEntradasCompradas";
	}

	@GetMapping("/misEntradas/{entradaId}")
	public String listDetallesEntradasCompradasUsuario(@PathVariable("entradaId") int entradaId, ModelMap model,
			Principal principal) {

		Entrada entrada = entradaService.findById(entradaId).orElse(null);

		Integer precio = entrada.getPrecio();

		Integer precioOfertas = entrada.getOfertas().stream().mapToInt(o -> o.getPrecioOferta()).sum();

		Integer precioTotal = precio + precioOfertas;
		model.addAttribute("entrada", entrada);
		model.addAttribute("precioTotal", precioTotal);
		model.addAttribute("ofertas", entrada.getOfertas());
		model.addAttribute("festival", entrada.getFestival());
		return "entradas/detallesEntrada";
	}

	@ModelAttribute("entradatype")
	public Collection<String> entradaTypes() {
		return entradaService.findEntradaTypes();

	}

	@InitBinder("entrada")
	public void initEntradaBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new EntradaValidator());
	}

	@GetMapping("mifestival/entradas/{id}/edit")
	public String editEntrada(@PathVariable("id") int id, ModelMap model) {

		Optional<Entrada> entrada = entradaService.findById(id);
		if (entrada.isPresent()) {
			model.addAttribute("entrada", entrada.get());
			return ENTRADAS_FORM;
		} else {
			model.addAttribute("message", "No podemos encontrar la entrada que intentas editar!");
			return listEntradas(model);
		}
	}

	@PostMapping("mifestival/entradas/{id}/edit")
	public String editEntrada(@PathVariable("id") int id, @Valid Entrada modifiedEntrada, BindingResult binding,
			ModelMap model, Principal principal) throws DataAccessException, AlcoholOfertAgeException {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();
		Optional<Entrada> entrada = entradaService.findById(id);
		if (binding.hasErrors()) {
			return ENTRADAS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedEntrada, entrada.get(), "id");
			EntradaType entradatype = this.entradaService.findEntradaType(modifiedEntrada.getEntradaType().getName());
			modifiedEntrada.setEntradaType(entradatype);
			modifiedEntrada.setFestival(this.festivalService.findFestivalById(festivalId).get());
			entradaService.save(modifiedEntrada);
			model.addAttribute("message", "entrada actualizada correctamente!");
			return "redirect:/mifestival";
		}
	}

//	@GetMapping("/{id}/delete")
//	public String deleteEntrada(@PathVariable("id") int id,@PathVariable("festivalId") int festivalId, ModelMap model) {
//		Optional<Entrada> entrada = entradaService.findById(id);
//		if (entrada.isPresent()) {
//			entradaService.delete(entrada.get());
//			model.addAttribute("message", "La entrada fue borrada correctamente!");
//			return "redirect:/festivales/{festivalId}";
//		} else {
//			model.addAttribute("message", "No podemos encontrar la entrada que intenta eliminar!");
//			return "redirect:/festivales/{festivalId}";
//		}
//	}

	@GetMapping("mifestival/entradas/new")
	public String createNewentrada(ModelMap model) {
		model.addAttribute("entrada", new Entrada());
		return ENTRADAS_FORM;
	}

	@PostMapping("mifestival/entradas/new")
	public String saveNewEntrada(@Valid Entrada entrada, BindingResult binding, ModelMap model, Principal principal) throws DataAccessException, AlcoholOfertAgeException {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (binding.hasErrors()) {
			return ENTRADAS_FORM;
		} else {
			EntradaType entradatype = entradaService.findEntradaType(entrada.getEntradaType().getName());
			entrada.setFestival(this.festivalService.findFestivalById(festivalId).get());
			entrada.setEntradaType(entradatype);
			entradaService.save(entrada);
			model.addAttribute("message", "La entrada fue creada correctamente!");
			return "redirect:/mifestival";
		}
	}
}

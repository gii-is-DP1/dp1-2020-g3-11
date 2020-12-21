package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.EntradaService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.UsuarioService;
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
		
		
		model.addAttribute("datosUsuario", usuario);
		model.addAttribute("datosFestival", festival);
		model.addAttribute("datosEntrada", entrada);
		return "entradas/entradaComprada";
	}

	@GetMapping("/festivales/{festivalId}/entradas/{entradaId}/gracias")
	public String graciasPorComprarEntrada(ModelMap model, @PathVariable("festivalId") int festivalId,
			@PathVariable("entradaId") int entradaId, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Entrada entrada = entradaService.findById(entradaId).orElse(null);
		
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
			ModelMap model, Principal principal) {

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
	public String saveNewEntrada(@Valid Entrada entrada, BindingResult binding, ModelMap model, Principal principal) {

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

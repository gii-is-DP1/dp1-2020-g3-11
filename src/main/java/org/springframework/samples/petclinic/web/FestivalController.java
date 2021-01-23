package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Puesto;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.EntradaService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.PuestoService;
import org.springframework.samples.petclinic.service.RecintoService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@EnableWebSecurity
public class FestivalController {

	public static final String FESTIVALES_FORM = "festivales/createOrUpdateFestivalForm";
	public static final String FESTIVALES_LISTING = "festivales/festivalListing";
	public static final String FESTIVALES_DETAILS = "festivales/festivalDetails";
	public static final String ARTISTAS_LISTA = "festivales/listArtistasAñadir";
	public static final String CARTEL = "festivales/cartel";
	public static final String ENTRADAS_FORM = "festivales/createOrUpdateEntradaForm";
	public static final String ALQUILAR_PUESTOS = "puestos/alquilarPuestos";
	public static final String PUESTOS_LISTING = "puestos/puestosListing";

	@Autowired
	PuestoService puestoService;

	@Autowired
	RecintoService festivalRecintoService;

	@Autowired
	FestivalService festivalService;

	@Autowired
	ArtistaService artistaService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	EntradaService festivalEntradaService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@GetMapping("/festivales")
	public String listFestivales(ModelMap model) {
		model.addAttribute("festivales", festivalService.findAll());
		return FESTIVALES_LISTING;
	}

	@GetMapping("/festivales/{id_festival}/puestos")
	public String listPuestosSponsor(Principal principal, @PathVariable("id_festival") int festivalId, ModelMap model) {
		Usuario usuario = usuarioLogueado(principal);
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Collection<Puesto> listaPuestos = puestoService.findPuestosLibres(festivalId);

		model.addAttribute("puestos", listaPuestos);
		model.addAttribute("datosUsuario", usuario);
		model.addAttribute("datosFestival", festival);

		return ALQUILAR_PUESTOS;
	}

	@GetMapping("/festivales/{festivalId}/puestos/{puestoId}/alquilar")
	public String alquilarPuestoSponsor(Principal principal, @PathVariable("festivalId") int festivalId,
			@PathVariable("puestoId") int puestoId, ModelMap model) {
		Usuario usuario = usuarioLogueado(principal);
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		
		Puesto puesto = puestoService.findById(puestoId).get();
		puesto.setSponsor(usuario);
		puestoService.save(puesto);
		
		Collection<Puesto> listaPuestos = puestoService.findPuestosLibres(festivalId);
		model.addAttribute("puestos", listaPuestos);
		model.addAttribute("datosUsuario", usuario);
		model.addAttribute("datosFestival", festival);
		model.addAttribute("message", "El puesto se asoció correctamente.");
		
		return ALQUILAR_PUESTOS;
	}

	@GetMapping("/mispuestos")
	public String listPuestosSponsor(Principal principal, ModelMap model) {
		Usuario usuario = usuarioLogueado(principal);

		model.addAttribute("puestos", puestoService.findAllPuestosBySponsorId(usuario.getId()));
		return PUESTOS_LISTING;
	}
	
	@GetMapping("/festivales/{festivalId}/cartel")
	public String listCartel(ModelMap model, @PathVariable("festivalId") int festivalId) {

		Collection<Artista> la = artistaService.findArtistasByFestivalId(festivalId);

		model.addAttribute("artistas", la);

		return CARTEL;
	}

	@GetMapping("/mifestival")
	public String showFestival(ModelMap model, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);

		Integer adminId = usuario.getId();
		Festival festival = festivalService.findFestivalByAdminId(adminId);

		model.addAttribute("festival", festival);
		model.addAttribute("artistas", artistaService.findArtistasByFestivalId(festival.getId()));
		model.addAttribute("recintos", festivalRecintoService.findAllRecintosByFestivalId(festival.getId()));
		model.addAttribute("entradas", festivalEntradaService.findAllEntradasByFestivalId(festival.getId()));

		return "/festivales/vistaFestival";
	}

	@GetMapping(value = "/mifestival/artistas/listdisponibles")
	public String listaArtistasDisponibles(ModelMap model, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Collection<Artista> artistas = artistaService.findAll();
		artistas.removeAll(artistaService.findArtistasByFestivalId(festivalId));
		model.addAttribute("artistasDisponibles", artistas);
		model.addAttribute("festival", festival);
		return ARTISTAS_LISTA;
	}

	@GetMapping(value = "/mifestival/artistas/{artistaId}/add")
	public String asociarArtistaFestival(@PathVariable("artistaId") int artistaId, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Artista artista = artistaService.findArtistaById(artistaId);

		if (!festival.getArtistas().contains(artista)) {
			festival.getArtistas().add(artista);
			festivalService.save(festival);
			artista.getFestivales().add(festival);
			artistaService.save(artista);

		}
		return "redirect:/mifestival";
	}

	@GetMapping("/mifestival/artistas/{artistaId}/delete")
	public String deleteArtistaDeFestival(@PathVariable("artistaId") int artistaId, ModelMap model,
			Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();
		Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
		Artista artista = artistaService.findArtistaById(artistaId);

		if (festival.getArtistas().contains(artista)) {
			festival.getArtistas().remove(artista);
			festivalService.save(festival);
			artista.getFestivales().remove(festival);
			artistaService.save(artista);
			model.addAttribute("message", "El artista fue borrado.");
			return showFestival(model, principal);
		} else {
			model.addAttribute("message", "No se encuentra el artista a borrar.");
			return showFestival(model, principal);
		}
	}

	@GetMapping("/mifestival/recintos/{recintoId}/delete")
	public String deleteRecintoDeFestival(@PathVariable("recintoId") int recintoId, ModelMap model,
			Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		Recinto recinto = festivalRecintoService.findByRecintoIdFestivalId(festivalId, recintoId);
		if (recinto != null) {
			festivalRecintoService.delete(recinto);
			model.addAttribute("message", "El recinto fue borrado.");
			return showFestival(model, principal);
		} else {
			model.addAttribute("message", "No se encuentra el recinto a borrar.");
			return showFestival(model, principal);
		}
	}

	@GetMapping("/mifestival/entradas/{entradaId}/delete")
	public String deleteEntradaDeFestival(@PathVariable("entradaId") int entradaId, ModelMap model,
			Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		Entrada entrada = festivalEntradaService.findByEntradaIdFestivalId(festivalId, entradaId);
		if (entrada != null) {
			festivalEntradaService.delete(entrada);
			model.addAttribute("message", "La entrada fue borrada.");
			return showFestival(model, principal);
		} else {
			model.addAttribute("message", "No se encuentra la entrada a borrar.");
			return showFestival(model, principal);
		}
	}

//	@GetMapping("/mifestival/edit")
//	public String editFestival(Principal principal, ModelMap model) {
//		Usuario usuario = usuarioLogueado(principal);
//		Integer festivalId = usuario.getFestival().getId();
//		Optional<Festival> festival = festivalService.findFestivalById(festivalId);
//		if (festival.isPresent()) {
//			model.addAttribute("festival", festival.get());
//			return FESTIVALES_FORM;
//		} else {
//			model.addAttribute("message", "No se encuentra el festival a editar.");
//			return showFestival(model, principal);
//		}
//	}
//
//	@PostMapping("/mifestival/edit")
//	public String editFestival(Principal principal, @Valid Festival modifiedFestival, BindingResult binding,
//			@RequestParam(value = "version", required = false) Integer version, ModelMap model) {
//		Usuario usuario = usuarioLogueado(principal);
//		Integer festivalId = usuario.getFestival().getId();
//		Optional<Festival> festival = festivalService.findFestivalById(festivalId);
//
//		if (binding.hasErrors()) {
//			return FESTIVALES_FORM;
//		} else {
//			Festival festivalBD = this.festivalService.findFestivalById2(modifiedFestival.getId());
//			if(festivalBD.getVersion() != version) {
//				model.put("message", "Modificación concurrente del festival, inténtelo más tarde por favor.");
//				return FESTIVALES_FORM;
//			}
//			BeanUtils.copyProperties(modifiedFestival, festival.get(), "id", "festivalAdmin");
//			modifiedFestival.incrementVersion();
//			festivalService.save(festival.orElse(null));
//			model.addAttribute("message", "Festival editado correctamente.");
//			return showFestival(model, principal);
//		}
//	}

//	@GetMapping("/mifestival/delete")
//	public String deleteFestival(Principal principal, ModelMap model) {
//		Usuario usuario = usuarioLogueado(principal);
//		Integer festivalId = usuario.getFestival().getId();
//		Optional<Festival> festival = festivalService.findFestivalById(festivalId);
//		if (festival.isPresent()) {
//			festivalService.delete(festival.get());
//			model.addAttribute("message", "Festival borrado correctamente.");
//			return "redirect:/";
//		} else {
//			model.addAttribute("message", "No se encuentra el festival a borrar.");
//			return "redirect:/";
//		}
//	}

//	@GetMapping("/festivales/new")
//	public String editNewFestival(ModelMap model) {
//		model.addAttribute("festival", new Festival());
//		return FESTIVALES_FORM;
//	}
//
//	@PostMapping("/festivales/new")
//	public String saveNewFestival(@Valid Festival festival, BindingResult binding, ModelMap model) {
//		if (binding.hasErrors()) {
//			return FESTIVALES_FORM;
//		} else {
//			festivalService.save(festival);
//			model.addAttribute("message", "Festival creado!");
//			return listFestivales(model);
//		}
//	}

}

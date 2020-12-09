package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Artista;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.FestivalArtista;
import org.springframework.samples.petclinic.model.Recinto;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ArtistaService;
import org.springframework.samples.petclinic.service.EntradaService;
import org.springframework.samples.petclinic.service.FestivalArtistaService;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.RecintoService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@EnableWebSecurity
public class FestivalController {

	public static final String FESTIVALES_FORM = "festivales/createOrUpdateFestivalForm";
	public static final String FESTIVALES_LISTING = "festivales/festivalListing";
	public static final String FESTIVALES_DETAILS = "festivales/festivalDetails";
	public static final String ARTISTAS_LISTA = "festivales/listArtistasAñadir";
	public static final String ENTRADAS_FORM = "festivales/createOrUpdateEntradaForm";

	@Autowired
	RecintoService festivalRecintoService;

	@Autowired
	FestivalService festivalService;

	@Autowired
	ArtistaService artistaService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	FestivalArtistaService festivalArtistaService;

	@Autowired
	EntradaService festivalEntradaService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@InitBinder("festival")
	public void initFestivalBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new FestivalValidator());
	}

	@GetMapping("/festivales")
	public String listFestivales(ModelMap model) {
		model.addAttribute("festivales", festivalService.findAll());
		return FESTIVALES_LISTING;
	}

	@GetMapping("/mifestival")
	public String showFestival(ModelMap model, Principal principal) {

		Usuario usuario = usuarioLogueado(principal);

		Integer adminId = usuario.getId();
		Festival festival = festivalService.findFestivalByAdminId(adminId);
		model.addAttribute("festival", festival);
		model.addAttribute("artistas", festivalArtistaService.findAllArtistasByFestivalId(festival.getId()));
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
		artistas.removeAll(festivalArtistaService.findAllArtistasByFestivalId(festivalId));
		model.addAttribute("artistasDisponibles", artistas);
		model.addAttribute("festival", festival);
		return ARTISTAS_LISTA;
	}

	@GetMapping(value = "/mifestival/artistas/{artistaId}/add")
	public String asociarArtistaFestival(@PathVariable("artistaId") int artistaId, Principal principal) {
		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (!festivalArtistaService.existByArtistaIdFestivalId(festivalId, artistaId)) {
			Festival festival = festivalService.findFestivalById(festivalId).orElse(null);
			Artista artista = artistaService.findArtistaById(artistaId);
			FestivalArtista fa = new FestivalArtista();
			fa.setArtista(artista);
			fa.setFestival(festival);
			festivalArtistaService.save(fa);
		}
		return "redirect:/mifestival";
	}

	@GetMapping("/mifestival/artistas/{artistaId}/delete")
	public String deleteArtistaDeFestival(@PathVariable("artistaId") int artistaId, ModelMap model,
			Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		FestivalArtista fa = festivalArtistaService.findByArtistaIdFestivalId(festivalId, artistaId);
		if (fa != null) {
			festivalArtistaService.delete(fa);
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

//	@GetMapping("/festivales/{id}/edit")
//	public String editFestival(@PathVariable("id") int id, ModelMap model) {
//		Optional<Festival> festival = festivalService.findFestivalById(id);
//		if (festival.isPresent()) {
//			model.addAttribute("festival", festival.get());
//			return FESTIVALES_FORM;
//		} else {
//			model.addAttribute("message", "No se encuentra el festival a editar.");
//			return listFestivales(model);
//		}
//	}

//	@PostMapping("/festivales/{id}/edit")
//	public String editFestival(@PathVariable("id") int id, @Valid Festival modifiedFestival, BindingResult binding,
//			ModelMap model) {
//		Optional<Festival> festival = festivalService.findFestivalById(id);
//		if (binding.hasErrors()) {
//			return FESTIVALES_FORM;
//		} else {
//			BeanUtils.copyProperties(modifiedFestival, festival.get(), "id");
//			festivalService.save(festival.get());
//			model.addAttribute("message", "Festival editado correctamente.");
//			return listFestivales(model);
//		}
//	}
//
//	@GetMapping("/festivales/{festivalId}/delete")
//	public String deleteFestival(@PathVariable("festivalId") int festivalId, ModelMap model) {
//		Optional<Festival> festival = festivalService.findFestivalById(festivalId);
//		if (festival.isPresent()) {
//			festivalService.delete(festival.get());
//			model.addAttribute("message", "Festival borrado correctamente.");
//			return listFestivales(model);
//		} else {
//			model.addAttribute("message", "No se encuentra el festival a borrar.");
//			return listFestivales(model);
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

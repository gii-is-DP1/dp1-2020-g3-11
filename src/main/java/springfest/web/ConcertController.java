package springfest.web;

import java.security.Principal ;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import springfest.model.Artista;
import springfest.model.Concert;
import springfest.model.Festival;
import springfest.model.Recinto;
import springfest.model.Usuario;
import springfest.service.ArtistaService;
import springfest.service.ConcertService;
import springfest.service.FestivalService;
import springfest.service.RecintoService;
import springfest.service.UsuarioService;
import springfest.service.exceptions.ConcertOutOfDateException;

@Slf4j
@Controller
@RequestMapping("mifestival/conciertos")
public class ConcertController {

	public static final String CONCERTS_FORM = "concerts/createOrUpdateConcertForm";
	public static final String CONCERTS_LISTING = "concerts/concertListing";
	
	private static final Logger logger =
			Logger.getLogger(ConcertController.class.getName());

	@Autowired
	ConcertService concertService;
	@Autowired
	RecintoService recintoService;
	@Autowired
	FestivalService festivalService;
	@Autowired
	ArtistaService artistService;

	@Autowired
	UsuarioService usuarioService;

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@Autowired
	public ConcertController(ConcertService concertService, FestivalService festivalService,
			ArtistaService artistService, RecintoService recintoService) {
		this.concertService = concertService;
		this.festivalService = festivalService;
		this.recintoService = recintoService;
		this.artistService = artistService;
	}

	@ModelAttribute("festival")
	public Festival findFestival(Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return this.festivalService.findFestivalById(festivalId).get();
	}

	@ModelAttribute("artistas")
	public Collection<String> artistas(Principal principal) {
		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return artistService.findArtistasByFestivalId(festivalId).stream().map(x -> x.getName())
				.collect(Collectors.toList());

	}

	@ModelAttribute("recintos")
	public Collection<String> recintos(Principal principal) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		return recintoService.findAllRecintosByFestivalId(festivalId).stream()
				.filter(x -> x.getTipoRecinto().getName().equals("Escenario")).map(x -> x.getName())
				.collect(Collectors.toList());
	}


	@InitBinder("concert")
	public void initConcertBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ConcertValidator());
	}

	@GetMapping
	public String listConcerts(Principal principal, ModelMap model) {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		model.addAttribute("concerts", concertService.findAllConcertsByFestivalId(festivalId));
		return CONCERTS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String initUpdateConcert(@PathVariable("id") int id, ModelMap model) {
		Concert concert = concertService.findById(id);
		model.put("concert", concert);
		return CONCERTS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String processUpdateConcert(@PathVariable("id") int id, Principal principal, @Valid Concert concert,
			BindingResult binding, @RequestParam(value = "version", required = false) Integer version, ModelMap model)
			throws DataAccessException, ConcertOutOfDateException {

		if (binding.hasErrors()) {
			model.put("concert", concert);
			return CONCERTS_FORM;

		} else {
			Concert concertDB = this.concertService.findById(id);
			if(concertDB.getVersion() != version) {
				model.put("message", "Modificación concurrente del concierto, inténtelo más tarde por favor.");
				return CONCERTS_FORM;
			}
			
			Concert modifiedConcert = concertService.findById(id);
			BeanUtils.copyProperties(concert, modifiedConcert, "id", "festival");
			Artista artista = this.artistService.findArtistaByName(modifiedConcert.getArtista().getName());
			Recinto recinto = this.recintoService.findRecintoByName(modifiedConcert.getRecinto().getName());
			try {
				modifiedConcert.setArtista(artista);
				modifiedConcert.setRecinto(recinto);
				modifiedConcert.incrementVersion();
				concertService.save(modifiedConcert);
			} catch (ConcertOutOfDateException e) {
				logger.log(Level.SEVERE, "Fallo haciendo", e);
				binding.rejectValue("fecha", "Tienes que crear un concierto en la franja horaria del festival.",
						"Tienes que crear un concierto en la franja horaria del festival.");
				return CONCERTS_FORM;
			}

		}
		return "redirect:/mifestival/conciertos";

	}

	@GetMapping("/{id}/delete")
	public String deleteConcert(@PathVariable("id") int id, Principal principal, ModelMap model) {

		Concert concert = concertService.findById(id);
		concertService.delete(concert);
		return "redirect:/mifestival/conciertos";
	}

	@GetMapping("/new")
	public String initCreationConcert(ModelMap model) {
		Concert concert = new Concert();
		model.put("concert", concert);
		return CONCERTS_FORM;
	}

	@PostMapping("/new")
	public String processCreationConcert(Principal principal, @Valid Concert concert, BindingResult binding,
			ModelMap model) throws DataAccessException, ConcertOutOfDateException {

		Usuario usuario = usuarioLogueado(principal);
		Integer festivalId = usuario.getFestival().getId();

		if (binding.hasErrors()) {
			return CONCERTS_FORM;
		} else {
			try {
				log.info("Creando concierto");
				concert.setRecinto(this.recintoService.findRecintoByName(concert.getRecinto().getName()));
				concert.setArtista(this.artistService.findArtistaByName(concert.getArtista().getName()));
				concert.setFestival(this.festivalService.findFestivalById(festivalId).get());
				this.concertService.save(concert);

			} catch (ConcertOutOfDateException e) {
				logger.log(Level.SEVERE, "Fallo haciendo", e);
				binding.rejectValue("fecha", "Tienes que crear un concierto en la franja horaria del festival.",
						"Tienes que crear un concierto en la franja horaria del festival.");
				return CONCERTS_FORM;

			}

		}

		return "redirect:/mifestival/conciertos";

	}
}

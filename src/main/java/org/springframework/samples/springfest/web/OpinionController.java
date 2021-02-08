package org.springframework.samples.springfest.web;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.springfest.model.Festival;
import org.springframework.samples.springfest.model.Opinion;
import org.springframework.samples.springfest.model.Usuario;
import org.springframework.samples.springfest.service.FestivalService;
import org.springframework.samples.springfest.service.OpinionService;
import org.springframework.samples.springfest.service.UsuarioService;
import org.springframework.samples.springfest.service.exceptions.OpinionFestivalDateException;
import org.springframework.samples.springfest.service.exceptions.OpinionNotAllowedException;
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
@RequestMapping("festivales/{festivalId}/valoraciones")
public class OpinionController {

	public static final String OPINIONS_LISTING = "opinions/opinionListing";
	public static final String OPINIONS_FORM = "opinions/createOpinionForm";

	@Autowired
	FestivalService festivalService;

	@Autowired
	OpinionService opinionService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	public OpinionController(FestivalService festivalService, OpinionService opinionService) {
		this.festivalService = festivalService;
		this.opinionService = opinionService;
	}

	public Usuario usuarioLogueado(Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.findUsuarioByUsername(username);
		return usuario;
	}

	@ModelAttribute("festival")
	public Optional<Festival> findFestival(@PathVariable("festivalId") int festivalId) {
		return this.festivalService.findFestivalById(festivalId);
	}

	@ModelAttribute("average")
	public Integer averageOpinions(@PathVariable("festivalId") int festivalId) {

		return this.opinionService.average(festivalId);
	}

	@InitBinder("opinion")
	public void initRecintoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new OpinionValidator());
	}

	@GetMapping
	public String listOpinions(ModelMap model, @PathVariable("festivalId") int festivalId) {

		model.addAttribute("opinions", this.opinionService.findOpinionsByFestivalId(festivalId));
		return OPINIONS_LISTING;
	}

	@GetMapping("/new")
	public String initCreationOpinion(ModelMap model) {
		Opinion opinion = new Opinion();
		model.put("opinion", opinion);
		return OPINIONS_FORM;
	}

	@PostMapping("/new")
	public String processCreationOpinion(@PathVariable("festivalId") int festivalId, @Valid Opinion opinion,
			BindingResult binding, ModelMap model, Principal principal)
			throws DataAccessException, OpinionNotAllowedException, OpinionFestivalDateException {

		if (binding.hasErrors()) {
			return OPINIONS_FORM;

		} else {
			try {
				
				Usuario usuario = usuarioLogueado(principal);
				opinion.setFestival(this.festivalService.findFestivalById(festivalId).get());
				opinion.setFecha(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
						LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(),
						LocalDateTime.now().getMinute()));
				opinion.setOpinionUsuario(usuario);
				this.opinionService.save(opinion);
				return "redirect:/festivales/{festivalId}/valoraciones";

			} catch (OpinionNotAllowedException e) {
				binding.rejectValue("descripcion", "Solo se pueden valorar festivales a los que se ha asistido.",
						"Solo se pueden valorar festivales a los que se ha asistido.");
				
				return OPINIONS_FORM;

			} catch (OpinionFestivalDateException e) {
				binding.rejectValue("descripcion", "Solo se pueden valorar festivales que hayan finalizado.",
						"Solo se pueden valorar festivales que hayan finalizado.");
				return OPINIONS_FORM;
			}

		}

	}
}

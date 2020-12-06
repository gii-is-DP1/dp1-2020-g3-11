package org.springframework.samples.petclinic.web;


import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Festival;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.service.FestivalService;
import org.springframework.samples.petclinic.service.OpinionService;
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
	public OpinionController(FestivalService festivalService, OpinionService opinionService) {
		this.festivalService = festivalService;
		this.opinionService = opinionService;
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
			BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			return OPINIONS_FORM;
			
		} else {
			opinion.setFestival(this.festivalService.findFestivalById(festivalId).get());
			opinion.setFecha(LocalDateTime.of(LocalDateTime.now().getYear(), 
					LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(),
					LocalDateTime.now().getHour(), LocalDateTime.now().getMinute()));
			
			this.opinionService.save(opinion);
			return "redirect:/festivales/{festivalId}/valoraciones";
		}
	}
}

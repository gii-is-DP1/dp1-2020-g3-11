package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Festival;
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

	@Autowired
	FestivalService festivalService;

	@GetMapping
	public String listFestivales(ModelMap model) {

		model.addAttribute("festivales", festivalService.findAll());
		return FESTIVALES_LISTING;
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

	@GetMapping("/{id}/delete")
	public String deleteFestival(@PathVariable("id") int id, ModelMap model) {
		Optional<Festival> festival = festivalService.findById(id);
		if (festival.isPresent()) {
			festivalService.delete(festival.get());
			model.addAttribute("message", "The Festival was deleted successfully!");
			return listFestivales(model);
		} else {
			model.addAttribute("message", "We cannot find the Festival you tried to delete!");
			return listFestivales(model);
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

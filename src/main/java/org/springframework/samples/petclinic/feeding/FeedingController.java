package org.springframework.samples.petclinic.feeding;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class FeedingController {
	private FeedingService feedingService;
	private PetService petService;
	
	@Autowired
	public FeedingController(FeedingService feedingService, PetService petService) {
		this.feedingService = feedingService;
		this.petService = petService;
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}
	@GetMapping(value = "/feeding/create")
	public String createUser(ModelMap modelMap) {
		modelMap.addAttribute("feeding", new Feeding());
		modelMap.addAttribute("feedingTypes",  this.feedingService.getAllFeedingTypes());
		//modelMap.addAttribute("types",  this.petService.findPetTypes());
		return "feedings/createOrUpdateFeedingForm";
	}
	@PostMapping(path = "/feeding/create")
	public String saveUser(@Valid Feeding feeding, BindingResult result, ModelMap model) throws UnfeasibleFeedingException {
		if (result.hasErrors()) {
			model.put("feedingTypes",  this.feedingService.getAllFeedingTypes());
			//model.put("types",  this.petService.findPetTypes());
			return "feedings/createOrUpdateFeedingForm";
		} else {
			feedingService.save(feeding);
			return "welcome";
		}	
	}
    
}

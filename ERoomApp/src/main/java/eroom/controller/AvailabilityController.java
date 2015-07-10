package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;

@Controller
public class AvailabilityController {

	
	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(){
		
		
		
		
		
		return "availability";
	}
	
	
}

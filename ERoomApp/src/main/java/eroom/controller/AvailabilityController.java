package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eroom.Links;
import eroom.calendar.Calendar;

@Controller
public class AvailabilityController {

	
	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(Model model){
		//Should maybe be the Cal from the session or a shared instance
		//Calendar cal = new Calendar();
		
		//model.addAttribute("rooms", cal.getRooms());
		
		return "availability";
	}
	
	
	//Send them on their way
	@RequestMapping(value="/goHome", method = RequestMethod.POST)
	public String goHome(){
		return "redirect:/";
	}
	
}

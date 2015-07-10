package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;
import eroom.calendar.Appointment;

@Controller
public class WelcomeController {
	
	@RequestMapping(Links.WELCOME)
	public String welcomeHome(Model model)  {		
		
		//Test latest appointment
		Appointment apt = new Appointment().withRoom("Room 1").withDescription("Desc 1").withOrganiser("Person 1");
		
		model.addAttribute("latestApt", apt);
		
		return "welcome";
	}
		
}

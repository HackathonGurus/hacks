package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.schedulable.User;

@Controller
public class WelcomeController {
	
	@RequestMapping(Links.WELCOME)
	public String welcomeHome(Model model)  {	

		User currentUser = Utils.getCurrentLoggedInUser();
		Appointment latestApt = Utils.getNextAppointmentFor(currentUser);
		
		model.addAttribute("currentUser", Utils.getCurrentLoggedInUser().getName());
		model.addAttribute("latestApt", latestApt);
		
		return "welcome";
	}
		
}

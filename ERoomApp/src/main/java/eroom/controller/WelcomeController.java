package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;

@Controller
public class WelcomeController {
	
	@RequestMapping(Links.WELCOME)
	public String welcomeHome()  {
		
		
		return "welcome";
	}
	
}

package eroom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eroom.Links;

@RestController
public class ApplicationController {
	
	//Send them on their way
	@RequestMapping(value="/goHome", method = RequestMethod.GET)
	public String goHome(){
		return "redirect:"+Links.WELCOME;
	}
	
	private static final String template = "Welcome to E-Room, %s!";
	
	@RequestMapping(value="/example")
	public String indexPage(@RequestParam(value="name", defaultValue="Demo User") String name) {
		return String.format(template,
				context(name) + " <br>Try this link - http://localhost:8080/?name='{your name}'");		
	}
	
	private String context(String name) {
		if(!name.equals("Demo User")) {
			return name + " The day is bright and sunny.";
		}
		return name;
	}
}

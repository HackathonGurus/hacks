package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;

@Controller
public class IndexController {
	
	@RequestMapping(Links.LOGIN)
	public String login(){		
		return "index";
	}
	
}

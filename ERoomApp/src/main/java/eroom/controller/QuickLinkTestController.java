package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;

@Controller
public class QuickLinkTestController {

	@RequestMapping(Links.QUICK_LINK_TEST)
	public String availabilityHome(Model model){
		
		
		model.addAttribute("links", Links.getLinks());
		
		
		return "quicklinktest";
	}
	
	
}

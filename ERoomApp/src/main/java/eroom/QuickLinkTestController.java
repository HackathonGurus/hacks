package eroom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuickLinkTestController {

	@RequestMapping(Links.QUICK_LINK_TEST)
	public String availabilityHome(Model model){
		
		
		model.addAttribute("links", Links.getLinks());
		
		
		return "quicklinktest";
	}
	
	
}

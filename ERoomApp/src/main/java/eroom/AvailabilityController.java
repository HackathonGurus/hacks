package eroom;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AvailabilityController {

	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(Model model){
		
		model.addAttribute("currentDateTime", new DateTime());
		
		

		return "availability";
	}
	
	//Send them on their way
	@RequestMapping(value="/goHome", method = RequestMethod.POST)
	public String goHome(){
		return "redirect:/";
	}
	
}


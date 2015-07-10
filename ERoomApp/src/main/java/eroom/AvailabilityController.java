package eroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AvailabilityController {

	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(){

		return "availability";
	}
	
	//Send them on their way
	@RequestMapping(value="/goHome", method = RequestMethod.POST)
	public String goHome(){
		return "redirect:/";
	}
	
}


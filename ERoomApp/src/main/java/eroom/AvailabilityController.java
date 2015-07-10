package eroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AvailabilityController {

	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(){
		
		
		
		
		
		return "availability";
	}
	
	
}

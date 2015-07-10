package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eroom.Links;
import eroom.calendar.Calendar;

@Controller
public class BookingDetailsController {

	
	@RequestMapping(Links.BOOKING_DETAILS)
	public String bookingsHome(Model model){
		//Should maybe be the Cal from the session or a shared instance
		//Calendar cal = new Calendar();
		
		//model.addAttribute("rooms", cal.getRooms());
		
		return "bookingDetails";
	}
	
	//Send them to availability page
	@RequestMapping(value="/back", method = RequestMethod.GET)
	public String goToAvailabilityPage(){
		return "redirect:"+Links.AVAILABILITY;
	}
	
}

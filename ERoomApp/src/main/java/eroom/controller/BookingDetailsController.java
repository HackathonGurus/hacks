package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.calendar.Calendar;

@Controller
public class BookingDetailsController {

   @RequestMapping(value=Links.BOOKING_DETAILS, method = RequestMethod.POST)
   public String addStudent(@RequestParam String appointmentId, 
   Model model) { 	   
	  for(Appointment appointment : Utils.getCurrentLoggedInUser().getAllAppointments()){
		  if(appointment.getId().equals(appointmentId)) {
		      model.addAttribute("appointment", appointment);
		  }
      } 
      return "bookingDetails";
   }
	
	//Send them to availability page
	@RequestMapping(value="/back", method = RequestMethod.GET)
	public String goToAvailabilityPage(){
		return "redirect:"+Links.AVAILABILITY;
	}
	
	//End Booking and send them to availability page
	@RequestMapping(value="/endBooking", method = RequestMethod.POST)
	public String endBooking(){
		//TODO: End Booking first before redirect
		return "redirect:"+Links.AVAILABILITY;
	}
	
}

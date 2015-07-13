package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eroom.ERoomAppApplication;
import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.calendar.Calendar;

@Controller
public class BookingDetailsController {
	
	

   @RequestMapping(value=Links.BOOKING_DETAILS, method = RequestMethod.POST)
   public String displayBookingDetails(@RequestParam String appointmentId, 
   Model model) { 
	   Appointment appointment = getAppointment(appointmentId);
	  if(appointment!= null) {
		  model.addAttribute("appointment", appointment);
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
	public String endBooking(@RequestParam String appointmentId){
		Appointment appointment = getAppointment(appointmentId);
		if(ERoomAppApplication.getCalendar().freeUpAppointment(appointment)) {
			//return "<script> alert('Booking was ended'); </script>";
			return "redirect:"+Links.AVAILABILITY;
		}
		//TODO: End Booking first before redirect
		return "redirect:"+Links.AVAILABILITY;
	}
	
	private Appointment getAppointment(String appointmentId) {
		  for(Appointment appointment : Utils.getCurrentLoggedInUser().getAllAppointments()){
			  if(appointment.getId().equals(appointmentId)) {
				  return appointment;
			  }
	      }
		return null;
	}
	
}

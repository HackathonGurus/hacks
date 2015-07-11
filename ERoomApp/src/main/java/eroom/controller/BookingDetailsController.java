package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eroom.Links;
import eroom.calendar.Appointment;
import eroom.calendar.Calendar;

@Controller
public class BookingDetailsController {

   @RequestMapping(value=Links.BOOKING_DETAILS, method = RequestMethod.POST)
   public String addStudent(@RequestParam String appointmentId, 
   Model model) { 
	  for(CalendarDay calDay : ERoomAppApplication.getCalendar().getRoom("").getDays().values()){
           for(Appointment apt : calDay.getBookings().values()){
                 if(StringUtils.equals(apt.getId(), str2)){
           }
      } 
	   
      model.addAttribute("organiser", selectedAppointment.getOrganiser());
      model.addAttribute("description", selectedAppointment.getDescription());
      model.addAttribute("room", selectedAppointment.getRoom());      
      model.addAttribute("attendees", selectedAppointment.getRequestedAttendees());      
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

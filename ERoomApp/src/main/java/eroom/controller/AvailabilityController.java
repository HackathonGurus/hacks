package eroom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eroom.Links;
import eroom.calendar.Appointment;
import eroom.calendar.Calendar;
import eroom.calendar.CalendarDay;

@Controller
public class AvailabilityController {
	
	@RequestMapping(Links.AVAILABILITY)
	public String availabilityHome(Model model){
		//Should maybe be the Cal from the session or a shared instance
		Calendar cal = new Calendar();
		
		List<Appointment> usersApointments = new ArrayList<Appointment>();
		
		//A test person
		usersApointments.add(new Appointment().withRoom("Rock"));
		
		//Use the first person for testing sake
		for(CalendarDay day : cal.getUsers().get(0).getDays().values()){
			for(Appointment apt : day.getBookings().values()){
				if(!apt.isFree()){
					usersApointments.add(apt);
				}		
			}
		}

		model.addAttribute("apointments", usersApointments);
		
		return "availability";
	}
		
}

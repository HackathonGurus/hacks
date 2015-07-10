package eroom.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.ERoomAppApplication;
import eroom.Links;
import eroom.calendar.Appointment;
import eroom.calendar.CalendarDay;

@Controller
public class RoomAvailabilityController {

	@RequestMapping(Links.ROOM_AVAILABILITY)
	public String availabilityHome(Model model){
		
		List<Appointment> roomAppointments = new ArrayList<Appointment>();
		
		//A test person
		roomAppointments.add(new Appointment().withRoom("Room 1").withDescription("Desc 1").withOrganiser("Person 1"));
		roomAppointments.add(new Appointment().withRoom("Room 2").withDescription("Desc 2").withOrganiser("Person 2"));
		roomAppointments.add(new Appointment().withRoom("Room 3").withDescription("Desc 3").withOrganiser("Person 3"));
		
		//Use the first person for testing sake
		CalendarDay day = ERoomAppApplication.getCalendar().getUsers().get(0).getDays().get(0);
		for(Appointment apt : day.getBookings().values()){
			roomAppointments.add(apt);		
		}

		model.addAttribute("appointments", roomAppointments);
		
		return "roomAvailability";
	}
	
}

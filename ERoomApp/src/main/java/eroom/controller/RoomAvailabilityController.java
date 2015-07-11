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
		
		CalendarDay day = ERoomAppApplication.getCalendar().getRooms().get(0).getDays().get(0);
		for(Appointment apt : day.getBookings().values()){
			roomAppointments.add(apt);		
		}

		model.addAttribute("appointments", roomAppointments);
		
		return "roomAvailability";
	}
	
}

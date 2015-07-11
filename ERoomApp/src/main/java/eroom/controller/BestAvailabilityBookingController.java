package eroom.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eroom.ERoomAppApplication;
import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.calendar.CalendarDay;
import eroom.schedulable.User;

@Controller
public class BestAvailabilityBookingController {

	@RequestMapping(Links.BEST_AVAIL_BOOKING)
    public String bestAvailableBookingHome(Model model){
        return "bestAvail";
    }
	
	//Yeeeeee hhaaaaaaa
	//Sucky cowboy code with not much though in error handling or displaying the abvailable to the user
	@RequestMapping(value=Links.BEST_AVAIL_BOOKING, method = RequestMethod.POST)
    public String bestAvailableBookingPost(
    		@RequestParam(required = true) String to,
    		@RequestParam(required = true) String subject,
    		@RequestParam(required = true) String details,
    		@RequestParam(required = true) String day){
		
		User currentUser = Utils.getCurrentLoggedInUser();
		String[] toSplit = splitAndTrim(to);

		
		//Not yet implimented day dropdown
		List<Appointment> availApts = ERoomAppApplication.getCalendar().getSuggestedAppointment(
				1, 
				currentUser.getName(), 
				toSplit);
		
		
		//Just book one for now and we can display a list of options later
		Appointment availAptOfInterest = availApts.get(0);
		
		Appointment aptToBook = new Appointment()			
			.withSummary(subject)
			.withDescription(details)
			.withRequestedAttendees(Arrays.asList(toSplit))
			.withRoom(availAptOfInterest.getRoom())
			.withOrganiser(currentUser.getName())
			
			//Choose the first one for now
			.withTimeSlot(availAptOfInterest.getTimeSlot())
			//Not yet implimented day dropdown
			.withDay(1);
			
		currentUser.bookAppointment(aptToBook);
		
			
        return "redirect:"+Links.WELCOME;
    }

	private String[] splitAndTrim(String stringToSplit) {
		String[] dirtySplits = StringUtils.split(stringToSplit, ",");
		List<String> results = new ArrayList<String>();
		
		for(String dirtySplit : dirtySplits){
			results.add(StringUtils.trim(dirtySplit));
		}
		
		return results.toArray(new String[results.size()]);
	}
    
}

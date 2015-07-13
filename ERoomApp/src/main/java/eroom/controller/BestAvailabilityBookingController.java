package eroom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import eroom.BookingButton;
import eroom.ERoomAppApplication;
import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.schedulable.User;

//For now stor this all on the session
@SessionAttributes({"appointmentId","availApts", "to", "subject", "details", "day"})

@Controller
public class BestAvailabilityBookingController {

	@RequestMapping(Links.BEST_AVAIL_BOOKING)
    public String bestAvailableBookingHome(Model model){		
        return "bestAvail";
    }
	
	
	@RequestMapping(value="/bestAvailBookById", method = RequestMethod.POST)
	public String bestAvailableBookingPost(Model model, String appointmentId) throws MessagingException, IOException{
		
		Appointment selectedAppointment = getAppointment(model, appointmentId);
		Map<String, Object> attributeMap = model.asMap();
		String[] toSplit = splitAndTrim((String)attributeMap.get("to"));
		
		populateAndBookBasedOnAnother(
				(String)attributeMap.get("subject"), 
				(String)attributeMap.get("details"), 
				(Integer)attributeMap.get("day"),
				toSplit, 
				selectedAppointment);
		
		nukeAllData(model);
		
		return "redirect:"+Links.AVAILABILITY;	
	}
	
	//Yeeeeee hhaaaaaaa
	//Sucky cowboy code with not much though in error handling or displaying the abvailable to the user
	@RequestMapping(value=Links.BEST_AVAIL_BOOKING, method = RequestMethod.POST)
    public String bestAvailableBookingPost(Model model,
    		@RequestParam(required = true) String to,
    		@RequestParam(required = true) String subject,
    		@RequestParam(required = true) String details,
    		@RequestParam(required = false) String quickBook,
    		@RequestParam(required = false) String find,
    		@RequestParam(required = true) int day) throws MessagingException, IOException{
		
		User currentUser = Utils.getCurrentLoggedInUser();
		String[] toSplit = splitAndTrim(to);

		
		//Not yet implimented day dropdown
		List<Appointment> availApts = ERoomAppApplication.getCalendar().getSuggestedAppointment(
				day, 
				currentUser.getName(), 
				toSplit);
		
		model.addAttribute("to", to);
		model.addAttribute("subject", subject);
		model.addAttribute("details", details);
		model.addAttribute("day", day);
		model.addAttribute("availApts", availApts);
		
		//If the click the quick book button
		if(quickBook != null){
			//Just book one for now and we can display a list of options later
			Appointment availAptOfInterest = availApts.get(0);
			
			populateAndBookBasedOnAnother(subject, details, day,
					toSplit, availAptOfInterest);
			
			nukeAllData(model);
			
			return "redirect:"+Links.AVAILABILITY;	
		}
		//Otherwise just continue and this controller takes care of displaying the available apts

        return "redirect:"+Links.BEST_AVAIL_BOOKING;
    }


	private void populateAndBookBasedOnAnother(String subject, String details,
			int day, String[] toSplit,
			Appointment availAptOfInterest) throws MessagingException,
			IOException {
		
		Appointment aptToBook = new Appointment()			
			.withSummary(subject)
			.withDescription(details)
			.withRequestedAttendees(Arrays.asList(toSplit))
			.withRoom(availAptOfInterest.getRoom())
			.withOrganiser(Utils.getCurrentLoggedInUser().getName())
			
			//Choose the first one for now
			.withTimeSlot(availAptOfInterest.getTimeSlot())
			//Not yet implimented day dropdown
			.withDay(day);
		
	
		//currentUser.bookAppointment(aptToBook);
		BookingButton bookingButton = new BookingButton();
		bookingButton.onClick(aptToBook);
	}
	
	private void nukeAllData(Model model){
		model.addAttribute("appointmentId", "");
		model.addAttribute("availApts", new ArrayList<Appointment>());
		model.addAttribute("to", "");
		model.addAttribute("subject", "");
		model.addAttribute("details", "details");
		model.addAttribute("day", "day");	
	}
	
	private Appointment getAppointment(Model model, String appointmentId) {
		List<Appointment> availApts = (List<Appointment>) model.asMap().get("availApts");
		
		  for(Appointment appointment : availApts){
			  if(appointment.getId().equals(appointmentId)) {
				  return appointment;
			  }
	      }
		  
		return null;
	}

	public static String[] splitAndTrim(String stringToSplit) {
		String[] dirtySplits = StringUtils.split(stringToSplit, ",");
		List<String> results = new ArrayList<String>();
		
		for(String dirtySplit : dirtySplits){
			results.add(StringUtils.trim(dirtySplit));
		}
		
		return results.toArray(new String[results.size()]);
	}
    
}

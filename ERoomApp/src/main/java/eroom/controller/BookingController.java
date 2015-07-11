package eroom.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eroom.BookingButton;
import eroom.ERoomAppApplication;
import eroom.Links;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;

@Controller
public class BookingController {

    @RequestMapping(Links.BOOKING)
    public String bookingHome(Model model){
        return "booking";
    }
    
    @RequestMapping(value=Links.BOOKING, method=RequestMethod.POST)
    public String bookAppt(@RequestParam(required = true) String to,
            @RequestParam(required = true) String subject,
            @RequestParam(required = true) String details,
            @RequestParam(required = true) String timeSlot,
            @RequestParam(required = true) String day,
            @RequestParam(required = true) String room) throws MessagingException, IOException {
        
        Appointment newApp = new Appointment();
        newApp.setMsgSubject(subject);
        newApp.setDescription(details);
        newApp.setTimeSlot(Integer.parseInt(String.valueOf(timeSlot.charAt(timeSlot.length() - 1))));
        newApp.setDay(Integer.parseInt(String.valueOf(day.charAt(day.length() - 1))));
        newApp.setOrganiser(Utils.getCurrentLoggedInUser().getName());
        newApp.setRequestedAttendees(Arrays.asList("user1"));
        newApp.setRoom(room);
        // ...and build appointment up
        
        if (ERoomAppApplication.getCalendar().areSchedulesFree(newApp)) {
            new BookingButton().onClick(newApp);
        }
        
        // TODO redirect somewhere different for successful bookings than for failed bookings
        return "redirect:" + Links.AVAILABILITY;
    }
    
}

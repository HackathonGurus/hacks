package eroom;

import java.util.List;

import eroom.calendar.Appointment;

public class BookingButton {
	
	public void sendEmail(Appointment a) {
		ERoomAppApplication.getCalendar().getUser(a.getOrganiser()).getEmailAddress();
		//a.getRequestedAttendees();
		List<String> emailAddresses = a.getRequestedAttendees();
		emailAddresses.add(ERoomAppApplication.getCalendar().getUser(a.getOrganiser()).getEmailAddress());

		
		// Construct iCalendar
		
		//Email(Multipart iCalendar, msgSubject, msgBody)
		//Emailer.Email(recipientEmailAddress, msgSubject, msgBody);
	}
	
	public void onClick() {
		Appointment appointment = new Appointment();
		ERoomAppApplication.getCalendar().bookAppointment(appointment);
		sendEmail(appointment);
	}

}

package eroom;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Multipart;

import eroom.Utility.Emailer;
import eroom.Utility.ICalendarConstructor;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;

public class BookingButton {

	public void sendEmail(Appointment appointment) throws MessagingException, IOException {
		String organizerEmail = ERoomAppApplication.getCalendar().getUser(appointment.getOrganiser()).getEmailAddress();
		//a.getRequestedAttendees();

		List<String> emailAddresses = appointment.getRequestedAttendees();

		StringBuffer sb = new StringBuffer();
		// Don't want to comma delimit if only one recipient
		if (emailAddresses.size() > 1) {
			for (String email : emailAddresses) {
			sb.append(email);
			sb.append(", ");
			}
			sb.replace(sb.length()-3, sb.length()-1, "");
		}
		String commaDelimitedEmailAddresses = sb.toString();// pass this to iCalendarConstructor
		// Construct iCalendar
		String iCalApointStartFormat = Utils.iCalendarDTStart(appointment.getDay(), appointment.getTimeSlot());
		
		String iCalApointEndFormat = Utils.iCalendarDTEnd(appointment.getDay(), appointment.getTimeSlot());//This needs to be timeslot end
		
		Multipart iCalendar = ICalendarConstructor.iCalendarConstructor(commaDelimitedEmailAddresses, organizerEmail, iCalApointStartFormat, iCalApointEndFormat, appointment.getRoom(), appointment.getDescription(), appointment.getSummary());
		
		//Email(Multipart iCalendar, msgSubject, msgBody)
		//Multipart iCalendar, String recipientEmailAddress, String msgSubject, String msgBody, String organizerEmail
			for (String email : appointment.getRequestedAttendees()) {
				Emailer.Email(iCalendar, email, appointment.getMsgSubject(), appointment.getMsgBody(), organizerEmail);
			}
	}

	public void onClick() throws MessagingException, IOException {
		Appointment appointment = new Appointment();
		ERoomAppApplication.getCalendar().bookAppointment(appointment);
		sendEmail(appointment);
	}

}

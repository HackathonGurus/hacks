package eroom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Multipart;

import eroom.Utility.Emailer;
import eroom.Utility.ICalendarConstructor;
import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.calendar.Calendar;
import eroom.schedulable.User;

public class BookingButton {
	
	/** List of all emails needed	 */
	public List<String> emailAddresses;
	
	public void sendEmail(Appointment appointment) throws MessagingException, IOException {
		Calendar cal = ERoomAppApplication.getCalendar();
		String organizerEmail = cal.getUser(appointment.getOrganiser()).getEmailAddress();
		//a.getRequestedAttendees();
		
		emailAddresses = new ArrayList<String>();
		for (String attendee : appointment.getRequestedAttendees()) {
			String userEmail = cal.getUser(attendee).getEmailAddress();

			emailAddresses.add(userEmail);
		}

		StringBuffer sb = new StringBuffer();
		// Don't want to comma delimit if only one recipient
		if (emailAddresses.size() > 1) {
			for (String email : emailAddresses) {
				sb.append(email);
				sb.append(", ");
			}
			sb.replace(sb.length()-3, sb.length()-1, "");
		
		} else if (emailAddresses.size() == 1) {
			sb.append(emailAddresses.get(0));
		}
		
		String commaDelimitedEmailAddresses = sb.toString();// pass this to iCalendarConstructor
		// Construct iCalendar
		String iCalApointStartFormat = Utils.iCalendarDTStart(appointment.getDay(), appointment.getTimeSlot());
		
		String iCalApointEndFormat = Utils.iCalendarDTEnd(appointment.getDay(), (appointment.getTimeSlot()+1));
		
		Multipart iCalendar = ICalendarConstructor.iCalendarConstructor(commaDelimitedEmailAddresses, organizerEmail, iCalApointStartFormat, iCalApointEndFormat, appointment.getRoom(), appointment.getDescription(), appointment.getSummary());
		
			for (String attendee : appointment.getRequestedAttendees()) {
				User user = ERoomAppApplication.getCalendar().getUser(attendee);
				
				
				Emailer.Email(iCalendar, user.getEmailAddress(), appointment.getMsgSubject(), appointment.getMsgBody(), organizerEmail);
			}
	}

	public void onClick(Appointment appointment) throws MessagingException, IOException {
		ERoomAppApplication.getCalendar().bookAppointment(appointment);
		sendEmail(appointment);
	}

}

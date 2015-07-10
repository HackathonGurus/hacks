package eroom;

import java.util.List;

import eroom.calendar.Appointment;

public class BookingButton {

	public void sendEmail(Appointment a) {
		ERoomAppApplication.getCalendar().getUser(a.getOrganiser()).getEmailAddress();
		//a.getRequestedAttendees();

		List<String> emailAddresses = a.getRequestedAttendees();

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

		//Email(Multipart iCalendar, msgSubject, msgBody)
		//Emailer.Email(recipientEmailAddress, msgSubject, msgBody);
	}

	public void onClick() {
		Appointment appointment = new Appointment();
		ERoomAppApplication.getCalendar().bookAppointment(appointment);
		sendEmail(appointment);
	}

}

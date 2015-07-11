package eroom.Utility;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class ICalendarConstructor {
	
	/**
	 * Construct the iCalendar
	 * TODO: this needs the local fields being set thats all
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static Multipart iCalendarConstructor(String commaDelimitedEmailAddresses, String organizerEmail, String appointStart, String appointEnd, String location, String description, String summary) throws MessagingException, IOException {
		StringBuffer sb = new StringBuffer();

        //Theres all sorts of crazy shit you can put in
        //RRULE:FREQ=YEARLY;BYDAY=1SU;BYMONTH=11
        //TZOFFSETFROM:GMT+1
        //TZOFFSETTO
        StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+commaDelimitedEmailAddresses+"\n" + //jordan@lyndons.net //TODO: pull in all recipients and file them in here
                "ORGANIZER:MAILTO:"+organizerEmail+"\n" + //jordan@lyndons.net
                "DTSTART:"+appointStart+"\n" + //20051208T053000Z
                "DTEND:"+appointEnd+"\n" + //20051208T060000Z
                "LOCATION:"+location+"\n" +
                "TRANSP:OPAQUE\n" +
                "SEQUENCE:0\n" +
                "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                " 000004377FE5C37984842BF9440448399EB02\n" +
                "DTSTAMP:20051206T120102Z\n" + //t
                "CATEGORIES:Meeting\n" +
                "DESCRIPTION:"+description+"\n\n" +
                "SUMMARY:"+summary+"\n" + 
                "PRIORITY:5\n" +
                "CLASS:PUBLIC\n" +
                "BEGIN:VALARM\n" +
                "TRIGGER:PT1440M\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:Reminder\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");

		// Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        messageBodyPart.setDataHandler(new DataHandler(
                new ByteArrayDataSource(buffer.toString(), "text/calendar")));// important

        // Create a Multipart
        Multipart multipart = new MimeMultipart();

        // add all that onto a multipart
        multipart.addBodyPart(messageBodyPart);
		return multipart;
	}
	
	

}

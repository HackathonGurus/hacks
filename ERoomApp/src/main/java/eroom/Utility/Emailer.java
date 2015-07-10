package eroom.Utility;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class Emailer {

	/** senders email */
	private String sendersAddress = "jordan@lyndons.net";
	//TODO: Change This to w/e

	/** just using locahost... */
	public static String HOST = "localhost";
    
    /** Sys Properties    */
    public Properties properties = System.getProperties();
    
    /** Session   */
    public Session session = Session.getDefaultInstance(properties);

    public String organizer; // appointment

	public String appointStart; //appoint 

	public String appointEnd; //appoint

	public String location; //location of meeting

	public String description; //meeting description

	public String summary; //summary for meeting

	public String recipientEmailAddress;//really
    
    /**
     * Email the iCalendar out.
     * 
     * @param recipientEmailAddress - really?
     * @param msgSubject - have a guess
     * @param msgBody - i wonder what this is
     * @throws AddressException -
     * @throws MessagingException -
     * @throws IOException -
     */
    public void Email(String recipientEmailAddress, String msgSubject, String msgBody) throws AddressException, MessagingException, IOException {
    	
    	Message msg = createMessage(recipientEmailAddress, msgSubject, msgBody);
    	
    	SendEmail(msg);
    }

    /**
     * Build the message we are sending 
     * 
     * @param recipientEmailAddress
     * @param msgSubject
     * @param msgBody
     * @return
     * @throws AddressException
     * @throws MessagingException
     * @throws IOException
     */
    private Message createMessage(String recipientEmailAddress, String msgSubject, String msgBody) throws AddressException, MessagingException, IOException {
    	
    	MimeMessage message = new MimeMessage(session);

    	//These are needed to identify that the email is an iCalendar formatted email
        message.addHeaderLine("method=REQUEST");
        message.addHeaderLine("charset=UTF-8");
        message.addHeaderLine("component=VEVENT");

    	//Who's sending the email
        message.setFrom(new InternetAddress(sendersAddress));

        // make sure its a valid email
        checkValidEmailAddress(recipientEmailAddress, message);

        // the subject of email
        message.setSubject("E-Room Meeting :" + msgSubject);

        buildMessageBody(message, msgBody);

    	return message;
    }
	
	/**
	 * construct the body with icalendar
	 * @param message
	 * @param msgBody
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void buildMessageBody(MimeMessage message, String msgBody) throws MessagingException, IOException {
		// any content in email
		BodyPart messageBody = new MimeBodyPart();
        messageBody.setText(msgBody);
        Multipart multipartBody = new MimeMultipart();
        multipartBody.addBodyPart(messageBody);
        
        //now add in iCalendar Shit
		Multipart multipart = iCalendarConstructor();

        // Put all parts into message
        message.setContent(multipart);
	}

	/**
	 * Construct the iCalendar
	 * TODO: this needs the local fields being set thats all
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private Multipart iCalendarConstructor() throws MessagingException, IOException {
		StringBuffer sb = new StringBuffer();

        //Theres all sorts of crazy shit you can put in
        //RRULE:FREQ=YEARLY;BYDAY=1SU;BYMONTH=11
        //TZOFFSETFROM=GMT+1
        //TZOFFSETTO
        StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+recipientEmailAddress+"\n" + //jordan@lyndons.net //TODO: pull in all recipients and file them in here
                "ORGANIZER:MAILTO:"+organizer+"\n" + //jordan@lyndons.net
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

	/**
	 * Fire Away!
	 * @param msg
	 */
	private void SendEmail(Message msg) {
	    // Setup mail server
	    properties.setProperty("mail.smtp.host", HOST);
	    
	    try{
	         // Send message
	         Transport.send(msg);
	         System.out.println("Sent message successfully to ...."+ msg.getAllRecipients());
	      }catch (MessagingException mex) {
	    	  System.out.println("Sending message failed to " + msg);
	         mex.printStackTrace();
	      }
	}

	/**
	 * bit of reg-ex, cause what doesn't say fun like a nice bit of reg-ex?
	 * ... although i remember tim saying that if we are regexing we are doing it wrong.......
	 * @param recipientEmailAddress
	 * @param message
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private void checkValidEmailAddress(String recipientEmailAddress, MimeMessage message)	throws MessagingException, AddressException {
		// ISO Internet Message Format
		Pattern rfc5322 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		
		if(rfc5322.matcher(recipientEmailAddress).matches()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
		} else {
			throw new IllegalArgumentException(recipientEmailAddress);
		}
	}
	
}

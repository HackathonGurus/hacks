package eroom.Utility;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

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

public class Emailer {

	/** just using locahost... */
	public static String HOST = "localhost";
    
    /** Sys Properties    */
    public static Properties properties = System.getProperties();
    
    /** Session   */
    public static Session session = Session.getDefaultInstance(properties);

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
    public static void Email(Multipart iCalendar, String recipientEmailAddress, String msgSubject, String msgBody, String organizerEmail) throws AddressException, MessagingException, IOException {
    	
    	Message msg = createMessage(iCalendar, recipientEmailAddress, msgSubject, msgBody, organizerEmail);
    	
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
    private static Message createMessage(Multipart iCalendar, String recipientEmailAddress, String msgSubject, String msgBody, String organizerEmail) throws AddressException, MessagingException, IOException {
    	
    	MimeMessage message = new MimeMessage(session);

    	//These are needed to identify that the email is an iCalendar formatted email
        message.addHeaderLine("method=REQUEST");
        message.addHeaderLine("charset=UTF-8");
        message.addHeaderLine("component=VEVENT");

    	//Who's sending the email
        message.setFrom(new InternetAddress(organizerEmail));

        // make sure its a valid email
        checkValidEmailAddress(recipientEmailAddress, message);

        // the subject of email
        message.setSubject("E-Room Meeting :" + msgSubject);

        buildMessageBody(message, msgBody, iCalendar);//down the rabbit hole

    	return message;
    }
	
	/**
	 * construct the body with icalendar
	 * @param message
	 * @param msgBody
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static void buildMessageBody(MimeMessage message, String msgBody, Multipart iCalendar) throws MessagingException, IOException {
		// any content in email
		BodyPart messageBody = new MimeBodyPart();
        messageBody.setText(msgBody);
        Multipart multipartBody = new MimeMultipart();
        multipartBody.addBodyPart(messageBody);
        
        //now add in iCalendar Shit
        Multipart multipart = iCalendar;

        message.setContent(multipart);
	}

	/**
	 * Fire Away!
	 * @param msg
	 */
	private static void SendEmail(Message msg) {
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
	private static void checkValidEmailAddress(String recipientEmailAddress, MimeMessage message)	throws MessagingException, AddressException {
		// ISO Internet Message Format
		Pattern rfc5322 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		
		if(rfc5322.matcher(recipientEmailAddress).matches()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
		} else {
			throw new IllegalArgumentException(recipientEmailAddress);
		}
	}
	
}

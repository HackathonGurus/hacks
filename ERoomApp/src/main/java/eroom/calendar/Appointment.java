package eroom.calendar;

import java.util.List;
import java.util.UUID;

import eroom.Utility.Utils;

/**
 * Hi friends
 * 
 * Appointment class - used to create and find calendar appointments.
 *
 * TODO need a way of turning an Appointment into a .ics file and vice-versa
 */
public class Appointment {

    /** The name of the organiser of the appointment */
    private String organiser;

    /** A brief description of the appointment */
    private String description;
    
    /** a brief summary of the appointment */
    private String summary;
    
    /** the subject line of the email */
    private String msgSubject;
    
    /** the body of the email */
    private String msgBody;

    /** The list of the names of all invitees */
    private List<String> requestedAttendees;

    /** The name of the room */
    private String room;

    /** The time slot for this appointment */
    private int timeSlot;

    /** The day index of the appointment */
    private int day;
    
    private final String id;

    public Appointment() {
    	this.id = UUID.randomUUID().toString();
    }
    
    public Appointment withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Appointment withOrganiser(String organiser) {
        this.organiser = organiser;
        return this;
    }

    public Appointment withRequestedAttendees(List<String> requestedAttendees) {
        this.requestedAttendees = requestedAttendees;
        return this;
    }

    public Appointment withRoom(String room) {
        this.room = room;
        return this;
    }

    public Appointment withDescription(String description) {
        this.description = description;
        return this;
    }

    public Appointment withTimeSlot(int timeSlot) {
        Utils.checkTimeSlotIsValid(timeSlot);
        this.timeSlot = timeSlot;
        return this;
    }

    public Appointment withDay(int day) {
        Utils.checkDayIsValid(day);
        this.day = day;
        return this;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public List<String> getRequestedAttendees() { //TODO: is this an email?
        return requestedAttendees;
    }

    public void setRequestedAttendees(List<String> requestedAttendees) { // TODO: is This an Email
        this.requestedAttendees = requestedAttendees;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public String getFormattedTimeSlot() {    	
        String formattedTimeSlot =  Utils.getFormattedTime(Utils.getSlotStartAsDateTime(getDay(), getTimeSlot())) + " - " + Utils.getFormattedTime(Utils.getSlotEndAsDateTime(getDay(), getTimeSlot()));
        return formattedTimeSlot;
    }

    /**
     * Convenience method to avoid littering the code with
     * <code>if (!(object instanceof EmptyAppointment)) {
     *     // do something
     * }</code>
     *
     * @return false
     */
    public boolean isFree() {
        return false;
    }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMsgSubject() {
		if(msgSubject != null){
			return msgSubject;
		}
		
		return summary;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}

	public String getMsgBody() {
		if(msgBody != null){
			return msgBody;
		}
		
		return description;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	
	public String getId() {
		return id;
	}
	
	public String convertDayToDay(int day) {
		return Utils.dayToDayString(day);
	}
	
	public String formatInvitees() {
		StringBuilder sb = new StringBuilder();
		for (String invitee : requestedAttendees) {
			sb.append(invitee);
			sb.append(", ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}
	
}

package eroom.calendar;

import java.util.List;

import eroom.Utility.Utils;

/**
 * Appointment class - used to create and find calendar appointments.
 *
 * TODO need a way of turning an Appointment into a .ics file and vice-versa
 */
public class Appointment {

    /** The name of the organiser of the appointment */
    private String organiser;

    /** A brief description of the appointment */
    private String description;

    /** The list of the names of all invitees */
    private List<String> requestedAttendees;

    /** The name of the room */
    private String room;

    /** The time slot for this appointment */
    private int timeSlot;

    /** The day index of the appointment */
    private int day;

    public Appointment() {

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

    public List<String> getRequestedAttendees() {
        return requestedAttendees;
    }

    public void setRequestedAttendees(List<String> requestedAttendees) {
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

}

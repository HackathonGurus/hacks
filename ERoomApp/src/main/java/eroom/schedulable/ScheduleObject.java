package eroom.schedulable;

import java.util.HashMap;

import eroom.Utility.Utils;
import eroom.calendar.Appointment;
import eroom.calendar.CalendarDay;

/**
 * Base class for an object that can have a schedule (e.g. a room, a user, etc)
 */
public class ScheduleObject {

    /** Map of day indices to CalendarDays */
    protected HashMap<Integer, CalendarDay> days;

    /**
     * Constructor initialises days to contains a set of blank CalendarDays
     */
    public ScheduleObject() {
        initialiseDays();
    }

    /**
     * @return the map of day indices to CalendarDays
     */
    public HashMap<Integer, CalendarDay> getDays() {
        return this.days;
    }

    /**
     * Initialises the days map to contain a pre-defined set of days, mapped to blank CalendarDays
     */
    protected void initialiseDays() {
        days = new HashMap<Integer, CalendarDay>();
        for (int day = 0; day < Utils.MAX_NUMBER_OF_DAYS; day++) {
            days.put(day, new CalendarDay(day));
        }
    }

    /**
     * Books an appointment in the appropriate calendar day
     *
     * @param appointment the appointment to book
     */
    public void bookAppointment(Appointment appointment) {
        Utils.checkDayIsValid(appointment.getDay());
        days.get(appointment.getDay()).bookSlot(appointment);
    }
}

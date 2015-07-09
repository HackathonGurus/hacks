package eroom.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eroom.Utils;

/**
 * Models one calendar for one day (i.e. the schedule for one room or user for a single day)
 */
public class CalendarDay {

    /** Map of time slot indices to Appointments */
    private HashMap<Integer, Appointment> bookings;

    /**
     * Constructor initialises bookings to contain a set of empty appointments
     *
     * @param day the day index of this calendar day
     */
    public CalendarDay(int day) {
        initialiseBookings(day);
    }

    /**
     * @return the map of time indices to appointments
     */
    public HashMap<Integer, Appointment> getBookings() {
        return this.bookings;
    }

    /**
     * Initialises bookings to all empty appointments
     *
     * @param day the day index of this CalendarDay
     */
    private void initialiseBookings(int day) {
        bookings = new HashMap<Integer, Appointment>();
        for (int index = 0; index < Utils.MAX_NUMBER_OF_TIME_SLOTS; index++) {
            bookings.put(index, new EmptyAppointment().withDay(day).withTimeSlot(index));
        }
    }

    /**
     * Books an appointment in this calendar day
     *
     * @param appointment the appointment to book
     */
    public void bookSlot(Appointment appointment) {
        Utils.checkTimeSlotIsValid(appointment.getTimeSlot());
        bookings.put(appointment.getTimeSlot(), appointment);
    }

    /**
     * @return a list of all free slots in this calendar day
     */
    public List<Integer> getFreeSlots() {
        List<Integer> freeSlots = new ArrayList<Integer>();
        for (int index : bookings.keySet()) {
            if (bookings.get(index).isFree()) {
                freeSlots.add(index);
            }
        }
        return freeSlots;
    }

    /**
     * @param slot the time slot to check
     * @return true if the given time slot is free
     */
    public boolean isSlotFree(int slot) {
        Utils.checkTimeSlotIsValid(slot);
        return bookings.get(slot).isFree();
    }
}

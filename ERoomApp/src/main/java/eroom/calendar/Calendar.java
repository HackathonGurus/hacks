package eroom.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import eroom.Utility.Utils;
import eroom.schedulable.Room;
import eroom.schedulable.ScheduleObject;
import eroom.schedulable.User;

/**
 * Big-ass God object models the Calendar for all users and rooms. Allows us to have a single object that we get all data from
 */
public class Calendar {

    /** List of pre-defined rooms */
    private List<Room> rooms;

    /** List of pre-defined users */
    private List<User> users;

    /**
     * Constructor initialises rooms and users to lists of those contained in the static final strings in Utils
     */
    public Calendar() {
        initialiseRooms();
        initialiseUsers();
        bookAppointment(new Appointment().withDay(0).withTimeSlot(0).withRequestedAttendees(Arrays.asList("user", "user1")).withDescription("Desc 0").withRoom("Room"));
        bookAppointment(new Appointment().withDay(1).withTimeSlot(1).withRequestedAttendees(Arrays.asList("user")).withDescription("Desc 1").withRoom("Room"));
        bookAppointment(new Appointment().withDay(2).withTimeSlot(2).withRequestedAttendees(Arrays.asList("user1")).withDescription("Desc 2").withRoom("Room"));
    }

    /**
     * Initialises the rooms list to the pre-defined rooms
     */
    private void initialiseRooms() {
        rooms = Utils.getRooms();
    }

    /**
     * Initialises the user list to the pre-defined users
     */
    private void initialiseUsers() {
        users = new ArrayList<User>();
        for (String name : Utils.getUserNames()) {
            users.add(new User(name));
        }
    }

    /**
     * @return the list of all rooms
     */
    public List<Room> getRooms() {
        return this.rooms;
    }

    /**
     * @return the list of all users
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Books an appointment. Makes sure all users specified on the appointment object are invited and the room is marked
     * as booked for the specified time slot. N.B. doesn't make the booking if the room is not already free
     *
     * @param appointment the appointment to book
     * @return true if the booking was successful
     */
    public boolean bookAppointment(Appointment appointment) {

        boolean appointmentIsValid = isAppointmentValid(appointment);
        boolean schedulesAreFree = areSchedulesFree(appointment);

        if (appointmentIsValid && schedulesAreFree) {
            getRoom(appointment.getRoom()).bookAppointment(appointment);
            getUser(appointment.getOrganiser()).bookAppointment(appointment);
            for (String attendeeName : appointment.getRequestedAttendees()) {
                getUser(attendeeName).bookAppointment(appointment);
            }
            return true;
        }

        return false;
    }

    /**
     * Checks if the variables on a given appointment are valid (i.e. time slot must be between 0 and 15, there are no
     * unknown users, etc.)
     *
     * @param appointment the appointment to check
     * @return true if the appointment is valid
     */
    public boolean isAppointmentValid(Appointment appointment) {

        try {

            Utils.checkRoomNameIsValid(appointment.getRoom());
            Utils.checkDayIsValid(appointment.getDay());
            Utils.checkTimeSlotIsValid(appointment.getTimeSlot());


        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a schedule is free for a given day, at a given time slot
     *
     * @param schedule the schedule to check
     * @param day the day on the schedule to check
     * @param timeSlot the time slot on the day on the schedule to check
     * @return true if the schedule is free at the specified day and time
     */
    public boolean isScheduleFree(ScheduleObject schedule, int day, int timeSlot) {
        return schedule.getDays().get(day).isSlotFree(timeSlot);
    }

    /**
     * Checks if a list of schedules are free for a given time slot on a given day
     *
     * @param day the day on the schedule to check
     * @param timeSlot the time slot on the day on the schedule to check
     * @param schedules the list of schedules to check
     * @return true if all schedules are free on the given day at the given time
     */
    public boolean areSchedulesFree(int day, int timeSlot, List<ScheduleObject> schedules) {
        for (ScheduleObject schedule : schedules) {
            if (!isScheduleFree(schedule, day, timeSlot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all schedules on a given appointment (i.e. room, organiser and invitees) are free to attend the
     * appointment in question
     *
     * @param appointment the appointment to check
     * @return true if the appointment is bookable
     */
    public boolean areSchedulesFree(Appointment appointment) {

        List<ScheduleObject> schedules = new ArrayList<ScheduleObject>();

        schedules.add(getRoom(appointment.getRoom()));
        schedules.add(getUser(appointment.getOrganiser()));
        for (String attendee : appointment.getRequestedAttendees()) {
            schedules.add(getUser(attendee));
        }

        return areSchedulesFree(appointment.getDay(), appointment.getTimeSlot(), schedules);
    }

    /**
     * Gets the room of given name
     *
     * @param name the name of the room to find
     * @return the room with the given name - null if no room exists with that name
     */
    public Room getRoom(String name) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(name)) {
                return room;
            }
        }
        return null; // Will never get here if we call Utils.checkRoomNameIsValid first
    }

    /**
     * Gets the user of given name
     *
     * @param name the name of the user to find
     * @return the user with the given name - null if no user exists with that name
     */
    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null; // Will never get here if we call Utils.checkUserNameIsValid first
    }

    /**
     * Gets a list of all slots free for a given set of users on a given day
     *
     * @param day the day to get common free slots on
     * @param userNames the names of the users to get the common free slots for
     * @return a list of all slot indexes on the given day that are free for all given users
     */
    public List<Integer> getCommonFreeSlots(int day, String... userNames) {

        Utils.checkDayIsValid(day);

        HashMap<Integer, List<Integer>> freeSlots = new HashMap<Integer, List<Integer>>();
        int index = 0;
        for (String userName : userNames) {
            freeSlots.put(index, getUser(userName).getDays().get(day).getFreeSlots());
            index++;
        }

        List<Integer> commonFreeSlots = new ArrayList<Integer>();

        // Loop over all slots free for the first user - if a slot is not in this list it cannot be a common free slot
        slotLoop: for (Integer slotIndex : freeSlots.get(0)) {

            // Loop over all other users and check that they are free for this slot from the first user's free slots
            for (int calendarIndex = 1; calendarIndex < freeSlots.size(); calendarIndex++) {

                // If they are not free, this slot is not a common free slot - skip to the next slot from the first user's list
                if (!freeSlots.get(calendarIndex).contains(slotIndex)) {
                    continue slotLoop;
                }
            }
            commonFreeSlots.add(slotIndex);
        }

        return commonFreeSlots;
    }

    /**
     * Gets a best suggested appointment on a given day for a given set of meeting attendees
     *
     * TODO return a list of all suggested appointments?
     *
     * @param day the day on which to book an appointment
     * @param organiser the name of the organiser of the meeting
     * @param attendees the name of the invitees
     * @return the best suggested appointment
     */
    public List<Appointment> getSuggestedAppointment(int day, String organiser, String... attendees) {

        String[] allAttendees = attendees.clone();
        allAttendees[allAttendees.length] = organiser;

        List<Integer> commonFreeSlots = getCommonFreeSlots(day, allAttendees);
        List<Room> roomsWithCapacity = getRoomsWithCapacity(allAttendees.length);
        Collections.sort(roomsWithCapacity);

        /*
         * For each common free slot, check if each room that has capacity for all attendees is free. The first free
         * room with capacity to hold all attendees at a time when all attendees are free is the best appointment to
         * book
         */
        List<Appointment> suggestedAppointments = new ArrayList<Appointment>();
        for (Integer slotIndex : commonFreeSlots) {
            for (Room room : roomsWithCapacity) {

                if (room.getDays().get(day).isSlotFree(slotIndex)) {
                    List<String> attendeeList = new ArrayList<String>();
                    Collections.addAll(attendeeList, attendees);
                    suggestedAppointments.add(new Appointment()
                                    .withDay(day)
                                    .withTimeSlot(slotIndex)
                                    .withRoom(room.getRoomName())
                                    .withOrganiser(organiser)
                                    .withRequestedAttendees(attendeeList));
                }
            }
        }

        return suggestedAppointments;
    }

    /**
     * Gets a list of all rooms with the capacity to hold a given number of attendees
     *
     * @param capacity the minimum capacity of the rooms
     * @return a list of rooms that can hold the given number of invitees
     */
    public List<Room> getRoomsWithCapacity(int capacity) {
        ArrayList<Room> roomsWithCapacity = new ArrayList<Room>();
        for (Room room : rooms) {
            if (room.getCapacity() >= capacity) {
                roomsWithCapacity.add(room);
            }
        }
        return roomsWithCapacity;
    }
}

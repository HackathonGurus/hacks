package eroom.calendar;

import java.util.Arrays;

/**
 * Subclass of Appointment with no organiser, description, invitees or room
 */
public class EmptyAppointment extends Appointment {

    public EmptyAppointment() {
        setOrganiser("");
        setDescription("");
        setRequestedAttendees(Arrays.asList(""));
        setRoom("");
    }

    /**
     * Convenience method to avoid littering the code with
     * <code>if (!(object instanceof EmptyAppointment)) {
     *     // do something
     * }</code>
     *
     * TODO could just have a null check in calendar day (e.g. if (bookings.get(slotIndex) == null) { // this appointment is empty })
     *
     * @return true
     */
    @Override
    public boolean isFree() {
        return true;
    }
}

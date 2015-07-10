package eroom.Utility;

import java.util.Arrays;
import java.util.List;

import eroom.schedulable.Room;

/**
 * General class containing constants and general useful methods
 *
 * TODO split into Constants and Utils
 */
public class Utils {

    /** The maximum number of time slots in a day. Assuming half-hour slots and 9-5 days, there are 16 time slots per day */
    public static final int MAX_NUMBER_OF_TIME_SLOTS = 16;
    /** The maximum number of days in a schedule. Completely arbitrary */
    public static final int MAX_NUMBER_OF_DAYS = 5;

    /* ROOM NAMES */
    public static final String ROOM_1 = "Room 1";
    public static final String ROOM_2 = "Room 2";
    public static final String ROOM_3 = "Room 3";
    public static final String ROOM_4 = "Room 4";
    public static final String ROOM_5 = "Room 5";

    /* ROOM CAPACITIES */
    public static final int ROOM_1_CAPACITY = 4;
    public static final int ROOM_2_CAPACITY = 4;
    public static final int ROOM_3_CAPACITY = 8;
    public static final int ROOM_4_CAPACITY = 8;
    public static final int ROOM_5_CAPACITY = 20;

    /* ROOM DESCRIPTIONS */
    public static final String ROOM_1_DESCRIPTION = "Room 1 Description";
    public static final String ROOM_2_DESCRIPTION = "Room 2 Description";
    public static final String ROOM_3_DESCRIPTION = "Room 3 Description";
    public static final String ROOM_4_DESCRIPTION = "Room 4 Description";
    public static final String ROOM_5_DESCRIPTION = "Room 5 Description";

    /* USER NAMES */
    public static final String USER_1 = "User 1";
    public static final String USER_2 = "User 2";
    public static final String USER_3 = "User 3";
    public static final String USER_4 = "User 4";
    public static final String USER_5 = "User 5";

    /**
     * Checks that a given time slot lies on the range [0, 16)
     *
     * @param timeSlot the time slot to check
     * @throws IllegalArgumentException if time slot is not on the range [0, 16)
     */
    public static void checkTimeSlotIsValid(int timeSlot) {
        if (timeSlot < 0 || timeSlot >= MAX_NUMBER_OF_TIME_SLOTS) {
            throw new IllegalArgumentException("Time slot " + timeSlot +  " is invalid - must be between zero and " + MAX_NUMBER_OF_TIME_SLOTS);
        }
    }

    /**
     * Checks that a given day lies on the range [0, 5)
     *
     * @param day the day to check
     * @throws IllegalArgumentException if day is not on the range [0, 5)
     */
    public static void checkDayIsValid(int day) {
        if (day < 0) {
            throw new IllegalArgumentException("Day " + day +  " is invalid - must be between zero and " + MAX_NUMBER_OF_DAYS);
        }
    }

    /**
     * Checks that a given user name is defined in a constant here
     *
     * @param name the user name to check
     * @throws IllegalArgumentException if the user name is not pre-defined
     */
    public static void checkUserNameIsValid(String name) {
        if (!getUserNames().contains(name)) {
            throw new IllegalArgumentException("Couldn't find user with name " + name);
        }
    }

    /**
     * Checks that a given room name is defined in a constant here
     *
     * @param name the room name to check
     * @throws IllegalArgumentException if the room name is not pre-defined
     */
    public static void checkRoomNameIsValid(String name) {
        if (!getRoomNames().contains(name)) {
            throw new IllegalArgumentException("Couldn't find room with name " + name);
        }
    }

    /**
     * @return a list of all pre-defined rooms
     */
    public static List<Room> getRooms() {
        return Arrays.asList(getRoom(1), getRoom(2), getRoom(3), getRoom(4), getRoom(5));
    }

    /**
     * @return a list of all pre-defined user names
     */
    public static List<String> getUserNames() {
        return Arrays.asList(USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    /**
     * @return a list of all pre-defined room names
     */
    public static List<String> getRoomNames() {
        return Arrays.asList(ROOM_1, ROOM_2, ROOM_3, ROOM_4, ROOM_5);
    }

    /**
     * Gets a room, given its index
     *
     * @param index the index of the room
     * @return the Room denoted by the given index
     */
    public static Room getRoom(int index) {
        switch (index) {
            case 1: return new Room(ROOM_1, ROOM_1_CAPACITY).withDescription(ROOM_1_DESCRIPTION);
            case 2: return new Room(ROOM_2, ROOM_2_CAPACITY).withDescription(ROOM_2_DESCRIPTION);
            case 3: return new Room(ROOM_3, ROOM_3_CAPACITY).withDescription(ROOM_3_DESCRIPTION);
            case 4: return new Room(ROOM_4, ROOM_4_CAPACITY).withDescription(ROOM_4_DESCRIPTION);
            case 5: return new Room(ROOM_5, ROOM_5_CAPACITY).withDescription(ROOM_5_DESCRIPTION);
            default: return null;
        }
    }
    
    public String slotToStartTimeString(int slotNo) {
		//slot 0 - 9:00
    	switch (slotNo) {

    	case 0:
    		return "090000Z";
    	case 1:
    		return "093000Z";
    	case 2:
    		return "100000Z";
    	case 3:
    		return "103000Z";
    	case 4:
    		return "110000Z";
    	case 5:
    		return "113000Z";
    	case 6:
    		return "120000Z";
    	case 7:
    		return "123000Z";
    	case 8:
    		return "130000Z";
    	case 9:
    		return "133000Z";
    	case 10:
    		return "140000Z";
    	case 11:
    		return "143000Z";
    	case 12:
    		return "150000Z";
    	case 13:
    		return "153000Z";
    	case 14:
    		return "160000Z";
    	case 15:
    		return "163000Z";
		default:
			return "090000Z";
		}
    }
    
    public String slotToEndTimeString(int slotNo) {
    	//slot 0 - 9:00
    	switch (slotNo) {
    	
    	case 0:
    		return "093000Z";
    	case 1:
    		return "100000Z";
    	case 2:
    		return "103000Z";
    	case 3:
    		return "110000Z";
    	case 4:
    		return "113000Z";
    	case 5:
    		return "120000Z";
    	case 6:
    		return "123000Z";
    	case 7:
    		return "130000Z";
    	case 8:
    		return "133000Z";
    	case 9:
    		return "140000Z";
    	case 10:
    		return "143000Z";
    	case 11:
    		return "150000Z";
    	case 12:
    		return "153000Z";
    	case 13:
    		return "160000Z";
    	case 14:
    		return "163000Z";
    	case 15:
    		return "170000Z";
    	default:
    		return "093000Z";
    	}
    }
    
    public String dayToDateString(int day) {
		switch (day) {
		case 0://Monday.
			return "20150713T";
		case 1://Tuesday.
			return "20150714T";
		case 2://Happy Days..
			return "20150715T";
		case 3://Thursday.
			return "20150716T";
		case 4://Friday.
			return "20150717T";

		default:
			return "20150713";
		}
    }
    
    public String iCalendarDTStart(int day, int timeToStart) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(dayToDateString(day));
		sb.append(slotToStartTimeString(timeToStart));

		return sb.toString();
    }

    public String iCalendarDTEnd(int day, int timeToStart) {
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(dayToDateString(day));
    	sb.append(slotToStartTimeString(timeToStart));
    	
    	return sb.toString();
    }
    
    
    
}

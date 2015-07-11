package eroom;

import java.util.ArrayList;
import java.util.List;

public final class Links {

	public static final String LOGIN = "/";
	public static final String AVAILABILITY = "/avail";
	public static final String WELCOME = "/welcome";
	public static final String QUICK_LINK_TEST = "/test";
	public static final String BOOKING_DETAILS = "/bookingDetails";
	public static final String ROOM_AVAILABILITY = "/room";
	public static final String ERROR = "/error";
	public static final String BOOKING = "/book";
	public static final String BEST_AVAIL_BOOKING = "/bestAvailBook";
	
	private static List<String> links  = new ArrayList<String>();
		
	static {
		links.add(LOGIN);
		links.add(AVAILABILITY);
		links.add(QUICK_LINK_TEST);
		links.add(WELCOME);
		links.add(BOOKING_DETAILS);
		links.add(ROOM_AVAILABILITY);
		links.add(ERROR);
		links.add(BOOKING);
		links.add(BEST_AVAIL_BOOKING);
	}	
	
	public static List<String> getLinks() {
		return links;
	}
	
}

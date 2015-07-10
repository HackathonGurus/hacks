package eroom;

import java.util.ArrayList;
import java.util.List;

public final class Links {

	public static final String AVAILABILITY = "/avail";
	public static final String WELCOME = "/welcome";
	public static final String QUICK_LINK_TEST = "/test";
	
	
	
	private static List<String> links  = new ArrayList<String>();
	
	
	static {
		links.add(AVAILABILITY);
		links.add(QUICK_LINK_TEST);
		links.add(WELCOME);
	}
	
	
	public static List<String> getLinks() {
		return links;
	}
	
}

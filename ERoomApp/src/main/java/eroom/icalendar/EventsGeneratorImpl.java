package eroom.icalendar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.SocketException;
import java.net.URI;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class EventsGeneratorImpl {
	
	CalendarBuilder builder = new CalendarBuilder();

	private Calendar parseCalendar(String myCalendarString) throws IOException, ParserException {
		StringReader sin = new StringReader(myCalendarString);
		return builder.build(sin);
	}
	
	private Calendar parseCalendar(File calendarFile) throws IOException, ParserException {
		FileInputStream fin = new FileInputStream(calendarFile);
		return builder.build(fin);
	}
	
	public Calendar createCalendar(VEvent event) {
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//bEN fORTUNA//IcAL4J 1.0//en"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);	
		calendar.getComponents().add(event);
		return calendar;
	}
	
	private void createMeetings(List<?> meetings) {
		//Be above to create iCalendar meetings from a list of Calendar duration 
		
	}
	
	public VEvent createAllDayEvent(String eventDescription, java.util.Calendar calendar) throws IOException {
		// initialise as an all-day event..
		VEvent event = new VEvent(new Date(calendar.getTime()), eventDescription);
		// Generate a UID for the event..		
		try {
			UidGenerator ug = new UidGenerator("1");
			event.getProperties().add(ug.generateUid());
		} catch (SocketException e) {
			e.printStackTrace();
		}		
		return event;
	}
	
	public Calendar createSpecificDurationEvent(java.util.Calendar startDate, java.util.Calendar endDate) {

		TimeZone timezone = generateTimeZone("America/Mexico_City");
		VTimeZone tz = timezone.getVTimeZone();
		
		// Create the event
		String eventName = "Progress Meeting";
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		VEvent meeting = new VEvent(start, end, eventName);

		// add timezone info..
		meeting.getProperties().add(tz.getTimeZoneId());

		// generate unique identifier..		
		try {
			UidGenerator ug = new UidGenerator("uidGen");
			Uid uid = ug.generateUid();
			meeting.getProperties().add(uid);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add attendees..
		Attendee dev1 = new Attendee(URI.create("mailto:dev1@mycompany.com"));
		dev1.getParameters().add(Role.REQ_PARTICIPANT);
		dev1.getParameters().add(new Cn("Developer 1"));
		meeting.getProperties().add(dev1);

		Attendee dev2 = new Attendee(URI.create("mailto:dev2@mycompany.com"));
		dev2.getParameters().add(Role.OPT_PARTICIPANT);
		dev2.getParameters().add(new Cn("Developer 2"));
		meeting.getProperties().add(dev2);

		// Create a calendar
		net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(Version.VERSION_2_0);
		icsCalendar.getProperties().add(CalScale.GREGORIAN);

		// Add the event and print
		icsCalendar.getComponents().add(meeting);
		//System.out.println(icsCalendar);
		return icsCalendar;
	}
	
	private java.util.Calendar startDate(TimeZone timezone) {
		 // Start Date is on: April 1, 2008, 9:00 am
		java.util.Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(timezone);
		startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
		startDate.set(java.util.Calendar.YEAR, 2008);
		startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
		startDate.set(java.util.Calendar.MINUTE, 0);
		startDate.set(java.util.Calendar.SECOND, 0);
		return startDate;
	}
	
	private java.util.Calendar endDate(TimeZone timezone) {
		 // End Date is on: April 1, 2008, 13:00
		java.util.Calendar endDate = new GregorianCalendar();
		endDate.setTimeZone(timezone);
		endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
		endDate.set(java.util.Calendar.YEAR, 2008);
		endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
		endDate.set(java.util.Calendar.MINUTE, 0);	
		endDate.set(java.util.Calendar.SECOND, 0);
		return endDate;
	}
		
	private List filterEventsForPeriod(Calendar calendar, java.util.Calendar forPeriod) {
		// create a period starting now with a duration of one (1) day..
		Period period = new Period(new DateTime(forPeriod.getTime()), new Dur(1, 0, 0, 0));
		Filter filter = new Filter(new PeriodRule(period));
		return (List)filter.filter(calendar.getComponents(Component.VEVENT));		
	}
	
	private TimeZone generateTimeZone(String timeZoneIdentifier) {
		// Create a TimeZone
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone(timeZoneIdentifier);
		return timezone;
	}
	
	public void generateCalendarFile (Calendar calendar) throws IOException, ValidationException {
		FileOutputStream fout = new FileOutputStream("./temp/eRoomCalendar.ics");
		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(calendar, fout);
	}
	
	public void generateEventFromCalendar (File calendarFile) throws ParseException, IOException, ParserException {
		Calendar cal = parseCalendar(calendarFile);

		// Create the date range which is desired.
		org.joda.time.DateTime today = new org.joda.time.DateTime(2015,06,01,0,0,0);
		org.joda.time.DateTime monthEnd = new org.joda.time.DateTime(2015,06,30,0,0,0);				
		
		DateTime from = new DateTime(today.toDate());
		DateTime to = new DateTime(monthEnd.toDate());
		Period period = new Period(from, to);		

		FileWriter writer = new FileWriter("./temp/icalLog.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
		// For each VEVENT in the ICS
		for (Object o : cal.getComponents("VEVENT")) {
			Component c = (Component)o;
			PeriodList list = c.calculateRecurrenceSet(period);
			for (Object po : list) {
				bufferedWriter.write(String.valueOf((Period)po));
			}
		}
		bufferedWriter.close();
		writer.close();
	}	
}

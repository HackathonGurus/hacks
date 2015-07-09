package eroom.icalendar.sample;

/* ICalendarExample.java */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

public class SampleEventsGeneratorRunner {

	public static void main(String[] args) throws IOException {
		
		String subject = "Hackathon";
		String location = "Location - Stockport";
		String description = "Hackathon Testing Appointment.";
		String hostEmail = "cam91940@myport.ac.uk";
		
		EventsGenerator generator = new EventsGenerator();
		

		File calFile = File.createTempFile("ERoomCalendar", ".ics", new File("./temp"));

		//start time
		java.util.Calendar startCal = java.util.Calendar.getInstance();
		startCal.set(2015, 06, 10, 20, 30);

		//end time
		java.util.Calendar endCal = java.util.Calendar.getInstance();
		endCal.set(2015, 06, 10, 20, 30);

		Calendar calendar = generator.createCalendar();

		SimpleDateFormat sdFormat =  new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
		String strDate = sdFormat.format(startCal.getTime());

		net.fortuna.ical4j.model.Date startDt = null;
		try {
			startDt = new net.fortuna.ical4j.model.Date(strDate,"yyyyMMdd'T'hhmmss'Z'");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
		int min = (int)(diff / (1000 * 60));

		Dur dur = new Dur(0,0,min,0);

		//Creating a meeting event
		VEvent meeting = new VEvent(startDt,dur,subject);

		meeting.getProperties().add(new Location(location));
		meeting.getProperties().add(new Description());

		try {
			meeting.getProperties().getProperty(Property.DESCRIPTION).setValue(description);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			meeting.getProperties().add(new Organizer("MAILTO:"+hostEmail));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		calendar.getComponents().add(meeting);

		FileOutputStream fout = null;

		try {
			fout = new FileOutputStream(calFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);

		try {
			outputter.output(calendar, fout);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		System.out.println(meeting);
	}
		
}

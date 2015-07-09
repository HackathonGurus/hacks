package eroom.icalendar;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;

public class EventsGeneratorRunner {

	public static void main(String[] args) {
		
		EventsGeneratorImpl generator = new EventsGeneratorImpl();
		try {
			//generator.generateEventDates(new File("./icsDumps/Williams Adeho Calendar.ics"));
			//generator.generateCalendarFile(generator.createDurationEvent());
			
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
			calendar.set(java.util.Calendar.DAY_OF_MONTH, 25);
			
			generator.generateCalendarFile(
					generator.createCalendar(
							generator.createAllDayEvent("Christmas", calendar)
					)
			);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " ERROR!!!");
		} 
	}
}

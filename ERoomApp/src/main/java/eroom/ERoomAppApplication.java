package eroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import eroom.calendar.Calendar;

@SpringBootApplication
public class ERoomAppApplication {
	
	private static final Calendar calendar = new Calendar();

    public static void main(String[] args) {
        SpringApplication.run(ERoomAppApplication.class, args);
    }
    
    public static Calendar getCalendar() {
    	return calendar;
    }
}

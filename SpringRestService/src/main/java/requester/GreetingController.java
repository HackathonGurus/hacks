package requester;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(method=RequestMethod.GET,value="/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="New User") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template,context(name)));
	}
	
	private String context(String name) {
		if(!name.equals("New User")) 
			return name + " The day is bright and sunny.";
		return name;
	}
}

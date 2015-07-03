package requester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={SpringRestServiceApplication.class})
public class SpringRestServiceApplication {

    public static void main(String[] args) {
    	//Enable line 11 below to run this application
//        SpringApplication.run(SpringRestServiceApplication.class, args);
    }
}

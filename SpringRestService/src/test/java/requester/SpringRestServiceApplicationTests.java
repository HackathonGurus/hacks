package requester;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import requester.SpringRestServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringRestServiceApplication.class)
@WebAppConfiguration
public class SpringRestServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}

package comu.community;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@PropertySource("classpath:/test.properties")
class CommunityApplicationTests {

	@Test
	void contextLoads() {
	}

}

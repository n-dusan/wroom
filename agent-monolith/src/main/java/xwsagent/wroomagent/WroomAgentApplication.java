package xwsagent.wroomagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class WroomAgentApplication {

	public static void main(String[] args) {

		SpringApplication.run(WroomAgentApplication.class, args);
		System.out.println("Agent monolith is running");
	}

}

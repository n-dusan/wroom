package xwsagent.wroomagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WroomAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WroomAgentApplication.class, args);
		System.out.println("Agent monolith is running");
	}

}

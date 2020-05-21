package xwsagent.wroomagent;

import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WroomAgentApplication {

	public static void main(String[] args) throws URISyntaxException {
//		File file = new File("/wroom-agent/src/main/resources/somefile.txt");
//		try {
//			BufferedReader bf = new BufferedReader(new FileReader(file));
//		} catch (FileNotFoundException e) {
//			System.out.println("ACCESS DENIED: somefile.txt");
//		}
		
		
		SpringApplication.run(WroomAgentApplication.class, args);
		System.out.println("Agent monolith is running");
	}

}

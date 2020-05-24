package xwsagent.wroomagent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WroomAgentApplication {

	public static void main(String[] args) throws URISyntaxException {

		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("somefile.txt"); 

			while (is.available() > 0) {	//read content from protected file
				System.out.print((char) is.read());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(WroomAgentApplication.class, args);
		System.out.println("Agent monolith is running");
	}

}

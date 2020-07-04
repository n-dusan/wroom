package xwsagent.wroomagent;

import java.net.URISyntaxException;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import xwsagent.wroomagent.domain.Message;
import xwsagent.wroomagent.service.RentReportService;
import xwsagent.wroomagent.soap.clients.MessagesClient;
import xwsagent.wroomagent.soap.xsd.SendMessageResponse;


@SpringBootApplication
public class WroomAgentApplication {

	public static void main(String[] args) throws URISyntaxException {

//		try {
//			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//			InputStream is = classloader.getResourceAsStream("somefile.txt"); 
//
//			while (is.available() > 0) {	//read content from protected file
//				System.out.print((char) is.read());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		

		SpringApplication.run(WroomAgentApplication.class, args);
		System.out.println("Agent monolith is running");
	}
	
//	@Bean
//	CommandLineRunner lookup(MessagesClient quoteClient) {
//		return args -> {
//			Message message = new Message();
//			message.setContent("Hey hey");
//			message.setTitle("Hey");
//			message.setFromUser("mila@maildrop.cc");
//			message.setToUser("zika@maildrop.cc");
//			message.setRentRequestId(1l);
//			message.setDate(new Date());
//			
//			try {
//				SendMessageResponse response = quoteClient.send(message);
//				System.err.println(response.getMessage().getTitle());
//			}catch (Exception e) {
//				System.out.println("EXCEPTION!!!!");
//				e.printStackTrace();
//			}
//		};
//	}

}

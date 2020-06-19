package xwsagent.wroomagent.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import xwsagent.wroomagent.soap.clients.CommentsClient;
import xwsagent.wroomagent.soap.clients.MessagesClient;
import xwsagent.wroomagent.soap.clients.RentsClient;

@Configuration
public class ClientConfig {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in pom.xml
		String [] packagesToScan = {"xwsagent.wroomagent.soap.xsd"};
		marshaller.setPackagesToScan(packagesToScan);
		return marshaller;
	}

	// When adding new clients, make sure to create a new Bean for them
	
	@Bean
	public MessagesClient messagesClient(Jaxb2Marshaller marshaller) {
		MessagesClient client = new MessagesClient();
		client.setDefaultUri("http://localhost:8075/ws");	// renting-service port
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public RentsClient rentsClient(Jaxb2Marshaller marshaller) {
		RentsClient client = new RentsClient();
		client.setDefaultUri("http://localhost:8075/ws");	// renting-service port
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public CommentsClient commentsClient(Jaxb2Marshaller marshaller) {
		CommentsClient client = new CommentsClient();
		client.setDefaultUri("http://localhost:8074/ws");	// ads-service port
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	
}

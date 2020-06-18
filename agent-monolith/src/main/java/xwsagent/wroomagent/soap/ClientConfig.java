package xwsagent.wroomagent.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import xwsagent.wroomagent.soap.clients.MessagesClient;

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

	@Bean
	public MessagesClient messagesClient(Jaxb2Marshaller marshaller) {
		MessagesClient client = new MessagesClient();
		client.setDefaultUri("http://localhost:8075/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	// When adding new clients, create a new Bean for them
	
}

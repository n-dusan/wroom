package xwsagent.wroomagent.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import xwsagent.wroomagent.soap.clients.AdsClient;
import xwsagent.wroomagent.soap.clients.CommentsClient;
import xwsagent.wroomagent.soap.clients.MessagesClient;
import xwsagent.wroomagent.soap.clients.RentsClient;
import xwsagent.wroomagent.soap.clients.VehicleClient;

@Configuration
public class ClientConfig {

	@Value("${uri_renting}")
	private String renting_uri;
	
	@Value("${uri_vehicle}")
	private String vehicle_uri;
	
	@Value("${uri_ads}")
	private String ads_uri;
	
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
	public VehicleClient vehicleClient(Jaxb2Marshaller marshaller) {
		VehicleClient client = new VehicleClient();
		client.setDefaultUri(this.vehicle_uri);	
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public MessagesClient messagesClient(Jaxb2Marshaller marshaller) {
		MessagesClient client = new MessagesClient();
		client.setDefaultUri(this.renting_uri);	
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public RentsClient rentsClient(Jaxb2Marshaller marshaller) {
		RentsClient client = new RentsClient();
		client.setDefaultUri(this.renting_uri);	
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public AdsClient adsClient(Jaxb2Marshaller marshaller) {
		AdsClient client = new AdsClient();
		client.setDefaultUri(this.ads_uri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public CommentsClient commentsClient(Jaxb2Marshaller marshaller) {
		CommentsClient client = new CommentsClient();
		client.setDefaultUri(this.ads_uri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	
}

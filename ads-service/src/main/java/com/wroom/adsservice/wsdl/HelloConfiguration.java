package com.wroom.adsservice.wsdl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class HelloConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.wroom.adsservice.wsdl");
		return marshaller;
	}

	@Bean
	public HelloClient countryClient(Jaxb2Marshaller marshaller) {
		HelloClient client = new HelloClient();
		client.setDefaultUri("http://localhost:8081/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
}

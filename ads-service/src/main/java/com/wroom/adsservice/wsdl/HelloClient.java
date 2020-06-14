package com.wroom.adsservice.wsdl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class HelloClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(HelloClient.class);

	public HelloResponse sayHello(String name) {

		HelloRequest request = new HelloRequest();
		request.setName(name);

		log.info("Requesting hello for " + name);

		HelloResponse response = (HelloResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8081/ws/hello", request,
						new SoapActionCallback(
								"http://localhost:8094/hello"));

		return response;
	}

	
}

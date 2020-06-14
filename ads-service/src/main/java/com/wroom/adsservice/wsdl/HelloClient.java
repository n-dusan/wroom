package com.wroom.adsservice.wsdl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class HelloClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(HelloClient.class);

	public HelloResponse sayHello(String name) {

		HelloRequest request = new HelloRequest();
		request.setName(name);

		System.out.println("Requesting hello for " + name);

		HelloResponse response = (HelloResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request);

		return response;
	}

	
}

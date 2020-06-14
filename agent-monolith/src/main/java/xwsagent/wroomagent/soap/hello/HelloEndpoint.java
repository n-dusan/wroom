package xwsagent.wroomagent.soap.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloEndpoint {
	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";

	private HelloRepository helloRepository;

	@Autowired
	public HelloEndpoint(HelloRepository helloRepository) {
		this.helloRepository = helloRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "hello")
	@ResponsePayload
	public HelloResponse hello(@RequestPayload HelloRequest request) {
		System.out.println(">>>>>>>>>>> I'm reached !");
		HelloResponse response = new HelloResponse();
		response.setHello(this.helloRepository.sayHello(request.getName()));
		return response;
	}
}

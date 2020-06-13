package xwsagent.wroomagent.soap.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloEndpoint {
	private static final String NAMESPACE_URI ="https://wroom.com/hello";

	private HelloRepository helloRepository;

	@Autowired
	public HelloEndpoint(HelloRepository helloRepository) {
		this.helloRepository = helloRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "helloRequest")
	@ResponsePayload
	public HelloResponse hello(@RequestPayload HelloRequest request) {
		HelloResponse response = new HelloResponse();
		response.setHello(this.helloRepository.sayHello(request.getName()));
		return response;
	}
}

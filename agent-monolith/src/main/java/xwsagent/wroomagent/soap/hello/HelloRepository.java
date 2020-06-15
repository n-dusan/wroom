package xwsagent.wroomagent.soap.hello;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class HelloRepository {

	public String sayHello(String name) {
		Assert.notNull(name, "Name must not be null");
		return "Hello " + name;
	}
	
}

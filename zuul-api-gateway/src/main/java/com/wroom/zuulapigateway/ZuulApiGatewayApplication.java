package com.wroom.zuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
public class ZuulApiGatewayApplication {


	@Bean
	public ZuulPreFilter simpleFilter() {
		return new ZuulPreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApiGatewayApplication.class, args);
		System.out.println("Zuul lives.");
	}

	@RestController
	public class HelloController {
		@GetMapping(value="/hello")
		public ResponseEntity<?> hi() {
			{
				return new ResponseEntity<>("Hi", HttpStatus.OK);
			}
		}
	}


}

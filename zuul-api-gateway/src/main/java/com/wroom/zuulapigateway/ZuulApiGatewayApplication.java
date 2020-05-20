package com.wroom.zuulapigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

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

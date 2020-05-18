package com.wroom.zuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

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
		System.setProperty("KEY_STORE_TYPE", "PKCS12");
		System.setProperty("KEY_STORE_CLASSPATH", "src/main/resources/zuul-server.p12");
		System.setProperty("KEY_STORE_PASSWORD", "password");
		System.setProperty("KEY_ALIAS", "655575225830");
		System.setProperty("EUREKA_INSTANCE_HOSTNAME", "localhost");
		System.setProperty("CLIENT_SERVICEURL_DEFAULTZONE", "https://localhost:8761/eureka/");
		System.setProperty("ZUUL_ROUTES_SEARCHSERVICE_SERVICEID", "search-service");
		System.setProperty("TRUST_STORE_TYPE", "PKCS12");
		System.setProperty("TRUST_STORE_CLASSPATH", "classpath:truststore.p12");
		System.setProperty("TRUST_STORE_PASSWORD", "classpath:password");
		SpringApplication.run(ZuulApiGatewayApplication.class, args);
		System.out.println("Zuul lives.");
	}

}

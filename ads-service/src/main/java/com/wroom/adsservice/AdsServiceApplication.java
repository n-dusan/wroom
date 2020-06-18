package com.wroom.adsservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.wroom.adsservice.wsdl.HelloClient;
import com.wroom.adsservice.wsdl.HelloResponse;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AdsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdsServiceApplication.class, args);
		System.out.println("Ad.");
	}

//	@Bean
//	CommandLineRunner lookup(HelloClient quoteClient) {
//		return args -> {
//			String name = "Tingle Girls";
//
//			if (args.length > 0) {
//				name = args[0];
//			}
//			
//			try {
//				HelloResponse response = quoteClient.sayHello(name);
//				System.err.println(response.getName());
//			}catch (Exception e) {
//				System.out.println("EXCEPTION!!!!");
//				e.printStackTrace();
//			}
//			
//			
//		};
//	}

}

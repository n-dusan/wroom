package com.wroom.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SearchServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
		System.out.println("Search service deployed");
	}

//	@Configuration
//	public class SSLConfig {
//		@Autowired
//		private Environment env;
//
//		@PostConstruct
//		private void configureSSL() {
//			//set to TLSv1.1 or TLSv1.2
////			System.setProperty("https.protocols", "TLSv1.2");
//
//			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
//			System.setProperty("javax.net.ssl.keyStore", "src/main/resources/search-server.p12");
//			System.setProperty("javax.net.ssl.keyStorePassword", "password");
//			System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
//			System.setProperty("javax.net.ssl.trustStore", "src/main/resources/truststore.p12");
//			System.setProperty("javax.net.ssl.trustStorePassword", "password");
//		}
//	}

}

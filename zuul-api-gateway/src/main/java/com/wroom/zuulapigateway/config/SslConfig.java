package com.wroom.zuulapigateway.config;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SslConfig {

//    @Bean
//    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
//        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
//        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/zuul-server.p12");
//        System.setProperty("javax.net.ssl.keyStorePassword", "password");
//        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
//        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/truststore.p12");
//        System.setProperty("javax.net.ssl.trustStorePassword", "password");
//        EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
//        builder.withClientName("655575225830");
//        builder.withSystemSSLConfiguration();
//        builder.withMaxTotalConnections(10);
//        builder.withMaxConnectionsPerHost(10);
//        args.setEurekaJerseyClient(builder.build());
//        return args;
//    }



}

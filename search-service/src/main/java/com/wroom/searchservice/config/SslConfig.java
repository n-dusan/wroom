package com.wroom.searchservice.config;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SslConfig {

    @Value("${server.ssl.key-store}")
    private File keyStore;
    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.trust-store}")
    private File trustStore;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    @Value("${server.ssl.enabled}")
    private boolean requireHttps;


    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
        if (requireHttps) {
            DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
            System.setProperty("javax.net.ssl.keyStore", "src/main/resources/search-server.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");
            System.setProperty("javax.net.ssl.trustStore", "src/main/resources/truststore.p12");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
            builder.withClientName("localhost");
            builder.withSystemSSLConfiguration();
            builder.withMaxTotalConnections(10);
            builder.withMaxConnectionsPerHost(10);
            args.setEurekaJerseyClient(builder.build());
            return args;
        }

        return null;
    }
}

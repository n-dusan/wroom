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
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException, IOException {
        if (requireHttps) {
            System.out.println("THE PATH IS: " + keyStore.getPath());
            System.out.println("TCAMONICANL HE PATH IS: " + keyStore.getCanonicalPath());
            DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.keyStore", keyStore.getPath());
            System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", trustStore.getPath());
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
            builder.withClientName("348124495475");
            builder.withSystemSSLConfiguration();
            builder.withMaxTotalConnections(10);
            builder.withMaxConnectionsPerHost(10);
            args.setEurekaJerseyClient(builder.build());
            return args;
        }

        return null;
    }
}

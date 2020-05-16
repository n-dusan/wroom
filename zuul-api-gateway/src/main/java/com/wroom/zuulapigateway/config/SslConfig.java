package com.wroom.zuulapigateway.config;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

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


    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        args.setSSLContext(sslContext);
        return args;
    }

    @Bean
    public SSLContext sslContext() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadKeyMaterial(
                keyStore,
                keyStorePassword.toCharArray(),
                keyStorePassword.toCharArray()
        );
        builder.loadTrustMaterial(
                trustStore,
                trustStorePassword.toCharArray()
        );
        return builder.build();
    }
}

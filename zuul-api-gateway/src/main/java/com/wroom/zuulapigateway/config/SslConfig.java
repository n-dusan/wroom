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


}

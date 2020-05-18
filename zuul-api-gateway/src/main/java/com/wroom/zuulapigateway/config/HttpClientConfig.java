package com.wroom.zuulapigateway.config;

import feign.Client;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class HttpClientConfig {

    @Bean
    public Client feignClient()
    {
        Client trustSSLSockets = new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
        return trustSSLSockets;
    }


    private SSLSocketFactory getSSLSocketFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            };

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            return sslContext.getSocketFactory();
        } catch (Exception exception) {
            //Do something
        }

        return null;
    }

//    @Bean
//    public CloseableHttpClient httpClient() throws Throwable {
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadTrustMaterial(new File("src/main/resources/truststore.p12"), "password".toCharArray(),
//                        new TrustSelfSignedStrategy())
//                .build();
//
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sslcontext,
//                new String[] { "TLSv1.3" },
//                null,
//                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//        return httpclient;
//    }

//    @Bean
//    public CloseableHttpClient httpClient() throws Throwable {
//
//        Boolean followRedirects = DefaultClientConfigImpl.DEFAULT_FOLLOW_REDIRECTS;
//        Integer connectTimeout = DefaultClientConfigImpl.DEFAULT_CONNECT_TIMEOUT;
//        RequestConfig defaultRequestConfig = RequestConfig.custom()
//                .setConnectTimeout(connectTimeout)
//                .setRedirectsEnabled(followRedirects).build();
//        return HttpClientBuilder.create()
//                .setMaxConnTotal(DefaultClientConfigImpl.DEFAULT_MAX_TOTAL_CONNECTIONS)
//                .setMaxConnPerRoute(DefaultClientConfigImpl.DEFAULT_MAX_CONNECTIONS_PER_HOST)
//                .disableContentCompression()
//                .disableCookieManagement()
//                .setDefaultRequestConfig(defaultRequestConfig)
//                .useSystemProperties()
//                .build();
//    }

}

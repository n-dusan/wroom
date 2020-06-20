package com.wroom.adsservice.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "ads")
	public DefaultWsdl11Definition defaultWsdl11DefinitionAds(XsdSchema helloSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AdsPort");
		wsdl11Definition.setTargetNamespace("http://ftn.com/wroom-agent/xsd");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(soapSchema());
		return wsdl11Definition;
	}

	@Bean(name = "comments")
	public DefaultWsdl11Definition defaultWsdl11DefinitionComments(XsdSchema helloSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CommentsPort");
		wsdl11Definition.setTargetNamespace("http://ftn.com/ads-service/xsd");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(soapSchema());
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema soapSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/soap.xsd"));
	}
	
}

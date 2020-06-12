package com.wroom.rentingservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/*
	 * Register Stomp endpoint. Uses SockJS library.
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket") 	// Endpoint will be used for clients to connect with server
				.setAllowedOrigins("*") 	// Change later
				.withSockJS(); 				// Use SockJS protocol
	}

	/*
	 * Clients who wish to use web socket broker must first connect to /socket-publisher
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/socket-subscriber") // Prefix that mapps all messages
				.enableSimpleBroker("/socket-publisher"); // Definisanje topic-a (ruta) na koje klijenti mogu da se pretplate.
														 // SimpleBroker cuva poruke u memoriji i salje ih klijentima na definisane topic-e.
														 // Server kada salje poruke, salje ih na rute koje su ovde definisane, a klijenti cekaju na poruke.
														 // Vise ruta odvajamo zarezom, npr. enableSimpleBroker("/ruta1", "/ruta2");
	}
	
}

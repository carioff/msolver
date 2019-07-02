package com.rootech.msolver.common.mqtt;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Profile("stomp")
//@EnableWebSocketMessageBroker
//@Configuration
//@EnableScheduling
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/stomp-chat").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/publish");
//        registry.enableSimpleBroker("/subscribe");
//    }

//    @Bean
//    public SimpMessagingTemplate brokerMessagingTemplate() {
//        SimpMessagingTemplate template = new SimpMessagingTemplate(brokerChannel());
//        String prefix = getBrokerRegistry().getUserDestinationPrefix();
//        if (prefix != null) {
//            template.setUserDestinationPrefix(prefix);
//        }
//    }
//    
//    @Bean
//    public SimpAnnotationMethodMessageHandler simpAnnotationMethodMessageHandler() {
//        SimpAnnotationMethodMessageHandler handler = createAnnotationMethodMessageHandler();
//        handler.setDestinationPrefixes(getBrokerRegistry().getApplicationDestinationPrefixes());
//    }
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp")
				.setAllowedOrigins("http://10.10.19.28:8080")
				.setAllowedOrigins("http://10.10.19.21:8080")
				.withSockJS()
				.setStreamBytesLimit(512 * 1024)
				.setHttpMessageCacheSize(1000)
				.setDisconnectDelay(30 * 1000);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/subscribe", "/topic");
	}

}
package com.keshrixlabs.neoanthrop.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*WebSocketMessageBrokerConfigurer interface has default methods,
    so you can implement the interface without needing to override any methods unless you want to customize the behavior.
    Default methods are methods defined in an interface with a default implementation.
    They allow interfaces to evolve by adding new methods without forcing all implementing classes to
    provide an implementation. When you implement an interface with default methods, you can choose to override
    these methods or use the provided default implementations.
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //communicator 1 <-- (message broker) --> communicator 2
        // A message broker in Spring WebSocket is a component that handles routing and delivery of
        // messages between clients and the server.

        registry.enableSimpleBroker("/user");
        //This enables a simple in-memory message broker that will handle messages with destinations starting with
        // "/user". The simple broker is suitable for basic WebSocket applications where you don't need advanced
        // message routing or persistence.
        registry.setApplicationDestinationPrefixes("/app");
        //This sets "/app" as the prefix for messages that are bound for @MessageMapping methods in your @Controller
        // classes. For example, if a client sends a message to "/app/hello", it will be routed to a controller method
        // mapped to "/hello".
        registry.setUserDestinationPrefix("/users");
        //configures the prefix for user-specific destinations. It's used for sending messages to specific users.
        // When you want to send a message to a particular user, Spring will use this prefix to handle the routing.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP is a simple text-orientated messaging protocol.
        // It defines a format for commands and messages that are sent between clients and servers.
        // message-oriented middleware (MOM) that provides message broking services like RabbitMQ and ActiveMQ.
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //This method is used to add custom argument resolvers to use in your WebSocket controllers.
        // Argument resolvers are used to resolve method parameters in your @MessageMapping methods.
        // You can add custom argument resolvers to handle custom types or to provide additional information to your
        // controller methods.
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}

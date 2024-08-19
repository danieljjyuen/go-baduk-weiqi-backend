//package org.example.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.graphql.server.WebGraphQlHandler;
//import org.springframework.graphql.server.webmvc.GraphQlWebSocketHandler;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import java.time.Duration;
//
//@Configuration
//@EnableWebSocket
//public class GraphQLWebSocketConfig implements WebSocketConfigurer {
//
//    private final WebGraphQlHandler webGraphQlHandler;
//
//    public GraphQLWebSocketConfig(WebGraphQlHandler webGraphQlHandler) {
//        this.webGraphQlHandler = webGraphQlHandler;
//    }
//
//    @Bean
//    public WebSocketHandler graphQLWebSocketHandler() {
//        return new GraphQlWebSocketHandler(
//                webGraphQlHandler,
//                new MappingJackson2HttpMessageConverter(),
//                Duration.ofMinutes(10)
//        );
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(graphQLWebSocketHandler(), "/graphql-ws")
//                .setAllowedOrigins("*");
//    }
//}

package custom.capstone.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket    // 웹소켓 서버 사용
@EnableWebSocketMessageBroker   // STOMP 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");    // 구독
        registry.setApplicationDestinationPrefixes("/pub");     // 발행
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws/chat")     // 엔드포인트: /ws/chat
                .setAllowedOrigins("*");
    }
}
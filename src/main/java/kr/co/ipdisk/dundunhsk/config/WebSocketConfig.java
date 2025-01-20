package kr.co.ipdisk.dundunhsk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(@SuppressWarnings("null") MessageBrokerRegistry config){
        // 클라이언트가 서버에 메시지를 전송할 때 사용하는 경로의 앞부분이다.
        config.setApplicationDestinationPrefixes("/send");
        // 전송받은 메시지를 전달해주는 "우체국"과 같은 역할을 하는 부분이다.
        config.enableSimpleBroker("/room");
    }

    @Override
    public void registerStompEndpoints(@SuppressWarnings("null") StompEndpointRegistry registry){
        // 브라우저가 WebSocket 통신을 지원하지 않더라도 연결할 수 있도록 도와주는 기능.
        registry.addEndpoint("/chat").withSockJS();
    }

}

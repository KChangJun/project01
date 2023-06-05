package com.groupc.weather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.groupc.weather.provider.WebSocketProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketProvider webSocketProvider;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {//웹소캣 핸들러를 등록하는 메서드

        registry.addHandler(webSocketProvider,"/web-socket")
        .setAllowedOrigins("*"); // 두번째 매개변수는 엔드포인트에 대한 것 
    
    }
    
}

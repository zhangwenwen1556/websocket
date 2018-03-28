package com.example.websocket.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                webSocketInterceptor()
        ).addPathPatterns("/**");
    }

    @Bean
    public WebSocketInterceptor webSocketInterceptor() {
        return  new WebSocketInterceptor();
    }

}

package com.example.profileservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                String token = (String) attrs.getAttribute("AUTH_TOKEN", RequestAttributes.SCOPE_REQUEST);
                if (token != null) {
                    template.header("Authorization", token);
                }
            }
        };
    }
}

package com.example.profileservice.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

@Component
public class FeignRequestTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null) {
            RequestContextHolder.currentRequestAttributes()
                    .setAttribute("AUTH_TOKEN", token, RequestAttributes.SCOPE_REQUEST);
        }

        chain.doFilter(request, response);
    }
}

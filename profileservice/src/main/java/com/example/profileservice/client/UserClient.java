package com.example.profileservice.client;


import com.example.profileservice.config.FeignConfig;
import com.example.profileservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userservice", url = "http://userservice:8080", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader);

}

package com.example.profileservice.client;

import com.example.dto.wilks.WilksRequestDTO;
import com.example.dto.wilks.WilksResponseDTO;
import com.example.profileservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "wilksservice", url="http://userservice:8082",configuration = FeignConfig.class)
public interface WilksClient {
    @PostMapping("/wilks/calculate")
    WilksResponseDTO calculateWilks(WilksRequestDTO requestDTO);

}

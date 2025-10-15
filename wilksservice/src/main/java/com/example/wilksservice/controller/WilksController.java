package com.example.wilksservice.controller;

import com.example.dto.wilks.WilksRequestDTO;
import com.example.dto.wilks.WilksResponseDTO;
import com.example.wilksservice.service.WilksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wilks")
public class WilksController {
    private final WilksService wilksService;

    public WilksController(WilksService wilksService) {
        this.wilksService = wilksService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<WilksResponseDTO> calculate(@RequestBody WilksRequestDTO requestDTO) {
        double score = wilksService.calculateWilks(requestDTO.getBodyWeight(), requestDTO.getTotalLifted(), requestDTO.getGender());
        return ResponseEntity.ok(new WilksResponseDTO(score));
    }
}

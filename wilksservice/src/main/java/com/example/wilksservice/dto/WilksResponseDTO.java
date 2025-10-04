package com.example.wilksservice.dto;

public class WilksResponseDTO {
    private double wilksScore;

    public WilksResponseDTO() {}

    public WilksResponseDTO(double wilksScore) {
        this.wilksScore = wilksScore;
    }

    public double getWilksScore() {
        return wilksScore;
    }
}

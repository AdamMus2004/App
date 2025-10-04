package com.example.wilksservice.dto;

import com.example.wilksservice.Gender;

public class WilksRequestDTO {
    private double bodyWeight;
    private double totalLift;
    private Gender gender;

    public WilksRequestDTO() {}

    public WilksRequestDTO(double bodyWeight, double totalLift, Gender gender) {
        this.bodyWeight = bodyWeight;
        this.totalLift = totalLift;
        this.gender = gender;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public double getTotalLift() {
        return totalLift;
    }

    public Gender getGender() {
        return gender;
    }
}

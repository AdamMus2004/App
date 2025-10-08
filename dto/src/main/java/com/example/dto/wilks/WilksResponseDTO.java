package com.example.dto.wilks;

import com.example.dto.Gender;

public class WilksResponseDTO {

    private Double wilksScore;
    private Double bodyWeight;
    private Gender gender;

    public WilksResponseDTO() {
    }

    public WilksResponseDTO(Double wilksScore, Double bodyWeight, Gender gender) {
        this.wilksScore = wilksScore;
        this.bodyWeight = bodyWeight;
        this.gender = gender;
    }

    public Double getWilksScore() {
        return wilksScore;
    }

    public void setWilksScore(Double wilksScore) {
        this.wilksScore = wilksScore;
    }

    public Double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

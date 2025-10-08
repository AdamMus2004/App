package com.example.dto.wilks;

import com.example.dto.Gender;
import jakarta.validation.constraints.NotNull;

public class WilksRequestDTO {

    @NotNull
    private Double bodyWeight;

    @NotNull
    private Double totalLifted;

    @NotNull
    private Gender gender;

    public WilksRequestDTO() {
    }

    public WilksRequestDTO(Double bodyWeight, Double totalLifted, Gender gender) {
        this.bodyWeight = bodyWeight;
        this.totalLifted = totalLifted;
        this.gender = gender;
    }

    public Double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Double getTotalLifted() {
        return totalLifted;
    }

    public void setTotalLifted(Double totalLifted) {
        this.totalLifted = totalLifted;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

package com.example.profileservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfileDTO {

    @NotBlank(message = "Bio cannot be empty")
    private String bio;

    @NotBlank(message = "Avatar URL cannot be empty")
    private String avatarUrl;

    ProfileDTO() {}

    public ProfileDTO(String bio, String avatarUrl) {
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }


    public String getBio() {
        return bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

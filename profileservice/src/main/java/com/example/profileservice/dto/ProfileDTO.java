package com.example.profileservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfileDTO {

    @NotNull(message = "userId cannot be null")
    private Long userId;

    @NotBlank(message = "Bio cannot be empty")
    private String bio;

    @NotBlank(message = "Avatar URL cannot be empty")
    private String avatarUrl;

    ProfileDTO() {}

    public ProfileDTO(Long userId, String bio, String avatarUrl) {
        this.userId = userId;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

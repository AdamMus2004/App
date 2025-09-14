package com.example.profileservice.dto;

public class ProfileResponseDTO {
    private Long id;
    private Long userId;
    private String bio;
    private String avatarUrl;

    public ProfileResponseDTO(Long id, Long userId, String bio, String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
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

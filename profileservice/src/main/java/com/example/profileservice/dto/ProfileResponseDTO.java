package com.example.profileservice.dto;

public class ProfileResponseDTO {
    private Long id;
    private Long userId;
    private String bio;
    private String avatarUrl;
    private UserResponseDTO user;

    public ProfileResponseDTO(Long id, Long userId, String bio, String avatarUrl, UserResponseDTO user) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.user = user;
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

    public UserResponseDTO getUser() {
        return user;
    }
}

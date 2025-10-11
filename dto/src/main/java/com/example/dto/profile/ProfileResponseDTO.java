package com.example.dto.profile;

public class ProfileResponseDTO {
    private Long id;
    private Long userId;
    private String bio;
    private String avatarUrl;
    private Double wilksScore;

    public ProfileResponseDTO(Long id, Long userId, String bio, String avatarUrl, Double wilksScore) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.wilksScore = wilksScore;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }
    public Double getWilksScore() { return wilksScore; }
}

package com.example.profileservice.service;

import com.example.dto.Gender;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileResponseDTO;
import com.example.dto.profile.UserResponseDTO;
import com.example.dto.wilks.WilksRequestDTO;
import com.example.dto.wilks.WilksResponseDTO;
import com.example.profileservice.client.UserClient;

import com.example.profileservice.client.WilksClient;
import com.example.profileservice.model.Profile;
import com.example.profileservice.repository.ProfileRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserClient userClient;
    private final ProfileRepository profileRepository;
    private final WilksClient wilksClient;

    public ProfileService(UserClient userClient, ProfileRepository profileRepository, WilksClient wilksClient) {
        this.userClient = userClient;
        this.profileRepository = profileRepository;
        this.wilksClient = wilksClient;
    }

    public List<ProfileResponseDTO> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(profile -> {
                    UserResponseDTO user = userClient.getUserById(profile.getUserId());
                    return new ProfileResponseDTO(profile.getId(), profile.getUserId(),
                            profile.getBio(), profile.getAvatarUrl(), profile.getWilksScore());
                })
                .collect(Collectors.toList());
    }

    public ProfileResponseDTO createProfile(ProfileDTO profileDTO) {
       UserResponseDTO user;
        try {
            user = userClient.getMe();
        } catch (FeignException fe) {
            throw new RuntimeException("Error calling userservice: " + fe.status(), fe);
        }

        if (profileRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        Profile profile = new Profile(user.getId(), profileDTO.getBio(), profileDTO.getAvatarUrl());
        Profile saved = profileRepository.save(profile);
        return new ProfileResponseDTO(saved.getId(), saved.getUserId(), saved.getBio(), saved.getAvatarUrl(), profile.getWilksScore());
    }


    public ProfileResponseDTO getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        UserResponseDTO user = userClient.getUserById(profile.getUserId());
        return new ProfileResponseDTO(profile.getId(), profile.getUserId(),
                profile.getBio(), profile.getAvatarUrl(), profile.getWilksScore());
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));

        UserResponseDTO currentUser;
        try {
            currentUser = userClient.getMe();
        } catch (FeignException fe) {
            throw new RuntimeException("Error verifying logged-in user in userservice", fe);
        }

        boolean isAdmin = "ADMIN".equalsIgnoreCase(currentUser.getRole());
        boolean isOwner = currentUser.getId().equals(profile.getUserId());

        if (!isAdmin && !isOwner) {
            throw new SecurityException("Only ADMIN or profile owner can delete this profile!");
        }

        profileRepository.delete(profile);
    }
    public ProfileResponseDTO updateWilksForLoggedUser(Double bodyWeight, Double totalLifted, String gender) {
        UserResponseDTO user = userClient.getMe();
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("Profile not found for user"));
        WilksRequestDTO request = new WilksRequestDTO(bodyWeight, totalLifted, Gender.valueOf(gender.toUpperCase()));
        WilksResponseDTO response = wilksClient.calculateWilks(request);
        profile.setWilksScore(response.getWilksScore());
        Profile updated = profileRepository.save(profile);
        return new ProfileResponseDTO(
                updated.getId(),
                updated.getUserId(),
                updated.getBio(),
                updated.getAvatarUrl(),
                updated.getWilksScore()
        );
    }

}

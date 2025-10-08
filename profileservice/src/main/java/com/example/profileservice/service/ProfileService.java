package com.example.profileservice.service;

import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileResponseDTO;
import com.example.dto.profile.UserResponseDTO;
import com.example.profileservice.client.UserClient;

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

    public ProfileService(UserClient userClient, ProfileRepository profileRepository) {
        this.userClient = userClient;
        this.profileRepository = profileRepository;
    }

    public List<ProfileResponseDTO> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(profile -> {
                    UserResponseDTO user = userClient.getUserById(profile.getUserId());
                    return new ProfileResponseDTO(profile.getId(), profile.getUserId(),
                            profile.getBio(), profile.getAvatarUrl(), user);
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

        if (profileRepository.findById(user.getId()).isPresent()) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        Profile profile = new Profile(user.getId(), profileDTO.getBio(), profileDTO.getAvatarUrl());
        Profile saved = profileRepository.save(profile);
        return new ProfileResponseDTO(saved.getId(), saved.getUserId(), saved.getBio(), saved.getAvatarUrl(), user);
    }


    public ProfileResponseDTO getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));
        UserResponseDTO user = userClient.getUserById(profile.getUserId());
        return new ProfileResponseDTO(profile.getId(), profile.getUserId(),
                profile.getBio(), profile.getAvatarUrl(), user);
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
}

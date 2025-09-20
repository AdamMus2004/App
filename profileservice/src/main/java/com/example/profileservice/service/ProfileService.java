package com.example.profileservice.service;

import com.example.profileservice.client.UserClient;
import com.example.profileservice.dto.ProfileDTO;
import com.example.profileservice.dto.ProfileResponseDTO;
import com.example.profileservice.dto.UserResponseDTO;
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

    public List<ProfileResponseDTO> getAllProfiles(String authHeader) {
        return profileRepository.findAll().stream().map(profile -> {
            UserResponseDTO user = userClient.getUserById(profile.getUserId(), authHeader);
            return new ProfileResponseDTO(profile.getId(),profile.getUserId(),profile.getBio(),profile.getAvatarUrl(),user);
        }).collect(Collectors.toList());
    }

    public ProfileResponseDTO createProfile(ProfileDTO profileDTO, String authHeader) {
        UserResponseDTO user;
        try {
            user = userClient.getUserById(profileDTO.getUserId(), authHeader);
        } catch (FeignException.NotFound nf) {
            throw new NoSuchElementException("User with id " + profileDTO.getUserId() + " not found");
        } catch (FeignException fe) {
            throw new RuntimeException("Error calling userservice: " + fe.status(), fe);
        }

        Profile profile = new Profile(profileDTO.getUserId(), profileDTO.getBio(), profileDTO.getAvatarUrl());
        Profile saved = profileRepository.save(profile);
        return new ProfileResponseDTO(saved.getId(), saved.getUserId(), saved.getBio(), saved.getAvatarUrl(), user);
    }

    public ProfileResponseDTO getProfileById(Long id, String authHeader) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Profile not found"));
        UserResponseDTO user = userClient.getUserById(profile.getUserId(), authHeader);
        return new ProfileResponseDTO(profile.getId(), profile.getUserId(), profile.getBio(), profile.getAvatarUrl(), user);
    }
    public void deleteProfile(Long id, String authHeader) {
        Profile profile = profileRepository.findById(id)
                        .orElseThrow(()-> new NoSuchElementException(("Profile not found")));
        UserResponseDTO user;
        try {
            user = userClient.getUserById(profile.getUserId(),authHeader);
        }catch (FeignException fe) {
            throw new RuntimeException("Error verifying user in userservice", fe);
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new SecurityException("Only ADMIN can delete profiles!");
        }
        profileRepository.delete(profile);
    }
}

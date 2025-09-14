package com.example.profileservice.controller;

import com.example.profileservice.dto.ProfileDTO;
import com.example.profileservice.dto.ProfileResponseDTO;
import com.example.profileservice.model.Profile;
import com.example.profileservice.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public List<ProfileResponseDTO> getAll() {
        return profileRepository.findAll().
                stream()
                .map(profile -> new ProfileResponseDTO(profile.getId(),profile.getUserId(),profile.getBio(),profile.getAvatarUrl()))
                .collect(Collectors.toList());
    }
    @PostMapping
    public ProfileResponseDTO create(@Valid @RequestBody ProfileDTO profileDTO) {
        Profile profile = new Profile(profileDTO.getUserId(), profileDTO.getBio(), profileDTO.getAvatarUrl());
        Profile saved = profileRepository.save(profile);
        return new ProfileResponseDTO(saved.getId(), saved.getUserId(), saved.getBio(),saved.getAvatarUrl());
    }

    @GetMapping("/{id}")
    public ProfileResponseDTO getProfile(@PathVariable Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow();
        return new ProfileResponseDTO(profile.getId(), profile.getUserId(), profile.getBio(), profile.getAvatarUrl());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        profileRepository.deleteById(id);
    }

}

package com.example.profileservice.controller;


import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileResponseDTO;
import com.example.dto.wilks.WilksRequestDTO;
import com.example.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileDTO profileDTO) {
        ProfileResponseDTO profile = profileService.createProfile(profileDTO);
        return ResponseEntity.status(201).body(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/update-wilks")
    public ResponseEntity<ProfileResponseDTO> updateWilks(@RequestBody WilksRequestDTO request) {
        return ResponseEntity.ok(profileService.updateWilksForLoggedUser(
                request.getBodyWeight(),
                request.getTotalLifted(),
                request.getGender().name()
        ));
    }

}

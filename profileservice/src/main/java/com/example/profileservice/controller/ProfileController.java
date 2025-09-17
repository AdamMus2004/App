package com.example.profileservice.controller;

import com.example.profileservice.dto.ProfileDTO;
import com.example.profileservice.dto.ProfileResponseDTO;
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
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles(){
        return ResponseEntity.ok(profileService.getAllProfiles());
    }
    @PostMapping
    public ProfileResponseDTO create(@Valid @RequestBody ProfileDTO profileDTO,
                                     @RequestHeader("Authorization") String authHeader) {
        return profileService.createProfile(profileDTO, authHeader);
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
}

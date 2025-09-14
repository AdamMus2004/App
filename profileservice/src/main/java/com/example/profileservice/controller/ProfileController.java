package com.example.profileservice.controller;

import com.example.profileservice.model.Profile;
import com.example.profileservice.repository.ProfileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    @PostMapping
    public Profile create(@RequestBody Profile profile) {
        return profileRepository.save(profile);
    }

    @GetMapping("/{id}")
    public Profile getProfile(@PathVariable Long id) {
        return profileRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        profileRepository.deleteById(id);
    }

}

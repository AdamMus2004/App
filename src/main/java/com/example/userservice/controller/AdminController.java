package com.example.userservice.controller;


import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.model.Role;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET
    @GetMapping("/users")
    public List<User> getAllAdminUsers() {
        return userRepository.findByRole(Role.ADMIN);
    }

    //GET
    @GetMapping("/users/{id}")
    public User getAdminUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/users")
    public User postAdminUser(@RequestBody User user) {
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //PUT
    @PutMapping("/users/{id}")
    public User putAdminUser(@RequestBody User user, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(user.getEmail());
                    existing.setName(user.getName());
                    existing.setPassword(user.getPassword());
                    return userRepository.save(existing);
                }).orElse(null);
    }
    //DELETE
    @DeleteMapping("/users/{id}")
    void deleteAdminUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
    @DeleteMapping("/users")
    void deleteAllAdmins(){
        List<User> admins = userRepository.findByRole(Role.ADMIN);
        userRepository.deleteAll(admins);
    }

}

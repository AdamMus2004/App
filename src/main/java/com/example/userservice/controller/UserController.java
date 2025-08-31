package com.example.userservice.controller;

import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findByRole(Role.USER);
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // POST /users
    @PostMapping
    public User postUser(@RequestBody User user) {
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    //PUT /users/{id}
    @PutMapping("/{id}")
    public User putUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(user.getName());
                    existing.setEmail(user.getEmail());
                    return userRepository.save(existing);
                })
                .orElse(null);
    }
    //DELETE /users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }


}

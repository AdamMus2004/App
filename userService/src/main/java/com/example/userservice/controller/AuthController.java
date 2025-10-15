package com.example.userservice.controller;

import com.example.dto.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error","Email already in use"));
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error","Password is required"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole(Role.USER);

        User saved = userRepository.save(user);
        return ResponseEntity.status(201).body(Map.of(
                "id", saved.getId(),
                "email", saved.getEmail(),
                "name", saved.getName(),
                "role", saved.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error","email and password required"));
        }

        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String token = authService.generateTokenForUser(user);
                    return ResponseEntity.ok(Map.of("token", token,"role",user.getRole()));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error","Invalid credential")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value="Authorization", required = false) String authHeader,
                                    @RequestBody(required=false) Map<String,String> body) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) token = authHeader.substring(7);
        if (token == null && body != null) token = body.get("token");
        if (token == null) return ResponseEntity.badRequest().body(Map.of("error","token required"));

        authService.removeToken(token);
        return ResponseEntity.ok(Map.of("message","Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).body(Map.of("error","Not authenticated"));
        return userRepository.findByEmail(authentication.getName())
                .map(user -> ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getName(),
                        "role", user.getRole()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMe(Authentication authentication, @RequestBody Map<String,String> updates) {
        if (authentication == null) return ResponseEntity.status(401).body(Map.of("error","Not authenticated"));

        return userRepository.findByEmail(authentication.getName())
                .map(user -> {
                    if (updates.containsKey("name")) user.setName(updates.get("name"));
                    if (updates.containsKey("password")) user.setPassword(passwordEncoder.encode(updates.get("password")));
                    userRepository.save(user);
                    return ResponseEntity.ok(Map.of("message","Profile updated"));
                })
                .orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getName(),
                        "role", user.getRole()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable("id") Long id, @RequestBody Map<String,String> body) {
        return userRepository.findById(id)
                .map(user -> {
                    try {
                        Role newRole = Role.valueOf(body.get("role").toUpperCase());
                        user.setRole(newRole);
                        userRepository.save(user);
                        return ResponseEntity.ok(Map.of("message","Role updated"));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(Map.of("error","Invalid role"));
                    }
                })
                .orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message","User deleted"));
        }
        return ResponseEntity.status(404).body(Map.of("error","User not found"));
    }
}

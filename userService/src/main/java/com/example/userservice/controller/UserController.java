package com.example.userservice.controller;


import com.example.userservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "email",user.getEmail(),
                        "name", user.getName(),
                        "role",user.getRole()
                ))).orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }
    // PUT /users/me
    @PutMapping("/me")
    public ResponseEntity<?> updateMyUSer(Authentication authentication, @RequestBody Map<String,String> updates) {
        return userRepository.findByEmail(authentication.getName())
                .map(user -> {
                    if (updates.containsKey("name")) user.setName(updates.get("name"));
                    if (updates.containsKey("password")) user.setPassword(passwordEncoder.encode(updates.get("password")));
                    userRepository.save(user);
                    return ResponseEntity.ok(Map.of("message", "Profile updated"));
                })
                .orElse(ResponseEntity.status(404).body(Map.of("error","User not found")));
    }
}

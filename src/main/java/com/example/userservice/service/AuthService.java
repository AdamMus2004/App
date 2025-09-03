package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String generateTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        tokens.put(token,user.getId());
        return token;
    }
    public Optional<User> getUserForToken(String token) {
        Long userId = tokens.get(token);
        if (userId == null) return Optional.empty();
        return userRepository.findById(userId);
    }
    public void removeToken(String token) {
        tokens.remove(token);
    }

}

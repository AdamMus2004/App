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
    private final class TokenData {
        Long userID;
        long expiresAt;
    }
    private final Map<String, TokenData> tokens = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        TokenData data = new TokenData();
        data.userID=user.getId();
        data.expiresAt = System.currentTimeMillis() + (60000);
        tokens.put(token,data);
        return token;
    }

    public Optional<User> getUserForToken(String token) {
        TokenData data = tokens.get(token);
        if (data == null || data.expiresAt < System.currentTimeMillis()) {
            tokens.remove(token);
            return Optional.empty();
        }
        return userRepository.findById(data.userID);
    }

    public void removeToken(String token) {
        tokens.remove(token);
    }
}

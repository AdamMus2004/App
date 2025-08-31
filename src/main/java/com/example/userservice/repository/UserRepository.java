package com.example.userservice.repository;

import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
}

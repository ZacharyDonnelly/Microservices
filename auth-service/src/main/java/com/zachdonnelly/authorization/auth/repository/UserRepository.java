package com.zachdonnelly.authorization.auth.repository;

import com.zachdonnelly.authorization.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByCustomerId(String customerId);
    List<User> getAllUsers();
}

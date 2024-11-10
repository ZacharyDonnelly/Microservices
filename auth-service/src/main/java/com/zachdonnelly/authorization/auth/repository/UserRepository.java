package com.zachdonnelly.authorization.auth.repository;

import com.zachdonnelly.authorization.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByCustomerId(String customerId);
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();
}

package com.zachdonnelly.authorization.auth.controller;

import com.zachdonnelly.authorization.auth.dto.UserRequest;
import com.zachdonnelly.authorization.auth.dto.UserResponse;
import com.zachdonnelly.authorization.auth.model.User;
import com.zachdonnelly.authorization.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllProducts() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByCustomerId(@PathVariable String customerId) {
        return userService.findByCustomerId(customerId);
    }

    @GetMapping(path = "/health")
    public String getHealthStatus(){
        return "OK";
    }
}

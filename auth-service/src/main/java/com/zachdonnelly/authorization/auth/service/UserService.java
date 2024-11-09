package com.zachdonnelly.authorization.auth.service;

import com.zachdonnelly.authorization.auth.repository.UserRepository;
import com.zachdonnelly.authorization.auth.dto.UserRequest;
import com.zachdonnelly.authorization.auth.dto.UserResponse;
import com.zachdonnelly.authorization.auth.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found: " + email);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Arrays.asList(authority));
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .customerId(userRequest.customerId())
                .password(userRequest.password())
                .role(userRequest.role())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .address1(userRequest.address1())
                .address2(userRequest.address2())
                .city(userRequest.city())
                .state(userRequest.state())
                .zip(userRequest.zip())
                .build();

        userRepository.save(user);
        log.info("User - " + userRequest.email() + " created successfully!");
        return new UserResponse(
                user.getCustomerId(),
                user.getPassword(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress1(),
                user.getAddress2(),
                user.getCity(),
                user.getState(),
                user.getZip()
        );
    }

    public UserResponse findByEmail(String email) {
       var foundUser = userRepository.findByEmail(email);

       if (foundUser == null) {
           log.error("User - " + email + " not found!");
           return null;
       }

        User user = User.builder()
                .customerId(foundUser.getCustomerId())
                .password(foundUser.getPassword())
                .role(foundUser.getRole())
                .firstName(foundUser.getFirstName())
                .lastName(foundUser.getLastName())
                .email(foundUser.getEmail())
                .address1(foundUser.getAddress1())
                .address2(foundUser.getAddress2())
                .city(foundUser.getCity())
                .state(foundUser.getState())
                .zip(foundUser.getZip())
                .build();

        log.info("User - " + foundUser.getEmail() + " found successfully!");

        return new UserResponse(
                user.getCustomerId(),
                user.getPassword(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress1(),
                user.getAddress2(),
                user.getCity(),
                user.getState(),
                user.getZip()
        );
    }

    public UserResponse findByCustomerId(String customerId) {
        var foundUser = userRepository.findByCustomerId(customerId);

        if (foundUser == null) {
            log.error("User - " + customerId + " not found!");
            return null;
        }

        User user = User.builder()
                .customerId(foundUser.getCustomerId())
                .password(foundUser.getPassword())
                .role(foundUser.getRole())
                .firstName(foundUser.getFirstName())
                .lastName(foundUser.getLastName())
                .email(foundUser.getEmail())
                .address1(foundUser.getAddress1())
                .address2(foundUser.getAddress2())
                .city(foundUser.getCity())
                .state(foundUser.getState())
                .zip(foundUser.getZip())
                .build();

        log.info("User - " + foundUser.getEmail() + " found successfully!");

        return new UserResponse(
                user.getCustomerId(),
                user.getPassword(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress1(),
                user.getAddress2(),
                user.getCity(),
                user.getState(),
                user.getZip()
        );
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}

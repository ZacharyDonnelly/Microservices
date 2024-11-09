package com.zachdonnelly.authorization.auth.config.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService userDetailsService;


    public CustomAuthenticationManager(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String userEmail = authentication.getName();
       String userPassword = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!userPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }

        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(
                userEmail, userPassword, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        return authenticatedUser;
    }

//        @Override
//        public boolean supports(Class<?> authentication) {
//            // Return true if this AuthenticationProvider supports the provided authentication class
//            return authentication.equals(UsernamePasswordAuthenticationToken.class);
//        }
    }

package com.zachdonnelly.authorization.auth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.zachdonnelly.authorization.auth.configuration.AuthConfig;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private AuthConfig config;

    @GetMapping(value = "/login")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUri = config.getContextPath(request) + "/callback";
        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .withScope("openid email")
                .build();
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping(value="/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException, IdentityVerificationException {
        Tokens tokens = authenticationController.handle(request, response);

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(), jwt.getToken());
        authToken2.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authToken2);
        response.sendRedirect(config.getContextPath(request) + "/");
    }

    @GetMapping(value = "/createUser")
    @ResponseBody
    public ResponseEntity<String> createUser(jakarta.servlet.http.HttpServletResponse response) {
        String url = "https://dev-wgint7l4qhqkps83.us.auth0.com/api/v2/users";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkxPYmNpZ0x5R3BjWkR4eFZNVUEtciJ9.eyJpc3MiOiJodHRwczovL2Rldi13Z2ludDdsNHFocWtwczgzLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJFYXNiVHNHZFR1NTJncG9uYmRSMzJoMnlZS1FSdzROTkBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9kZXYtd2dpbnQ3bDRxaHFrcHM4My51cy5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTczMTI4ODY4MCwiZXhwIjoxNzMxMzc1MDgwLCJzY29wZSI6InJlYWQ6dXNlcnMgY3JlYXRlOnVzZXJzIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIiwiYXpwIjoiRWFzYlRzR2RUdTUyZ3BvbmJkUjMyaDJ5WUtRUnc0Tk4ifQ.NZgsFSWtZDLRfusZ5aga0UcAtkCI1Q6VA0I-g6SaN7HJmq7gQHe0bPr7KlEgHRbYA4FfT3vbqf2wOSAYHPlIHJdnsZUk50KqLBUaQrqjpw7EXE6nPhGQO1YxrB7xsA7zvWLqAYvgOllrDaoRwb7PyEq6hbLqvVsMo4dZOgZ214tlzvMfumkGyR5JUllRjflGqSyQtkBar-3W-WDij2gg0eLQry4f4_ohjAdTngMepueBHVJdUtB9NW5QJuX1Os7t6__scSZDKhNXQfyxXudEf-_eLPsA3iiybOUde-BFVCk1_Wja7zs0AUc1qM1f9V25ZxiQrB-nu_qIRhT4Q0_3oQ"; // Replace with your actual token

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"password\":\"Pa33w0rd\",\"connection\":\"Username-Password-Authentication\",\"given_name\":\"Norman\",\"family_name\":\"Lewis\",\"email\":\"norman.lewis@email.com\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

}

package com.zachdonnelly.authorization.auth.controller;

import com.zachdonnelly.authorization.auth.configuration.AuthConfig;
import com.zachdonnelly.authorization.auth.dto.UserRequest;
import com.zachdonnelly.authorization.auth.dto.UserResponse;
import com.zachdonnelly.authorization.auth.model.User;
import com.zachdonnelly.authorization.auth.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    private AuthConfig config;

    private static final String AUTH0_TOKEN_URL = "https://dev-wgint7l4qhqkps83.us.auth0.com/oauth/token";


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping(value="/users")
    @ResponseBody
    public ResponseEntity<String> users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getManagementApiToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
                .exchange("https://dev-wgint7l4qhqkps83.us.auth0.com/api/v2/users", HttpMethod.GET, entity, String.class);
        log.info("Bearer " + getManagementApiToken());
        return result;
    }

    @GetMapping(value = "/all")
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

    public String getManagementApiToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", config.getManagementApiClientId());
        requestBody.put("client_secret", config.getManagementApiClientSecret());
        requestBody.put("audience", "https://dev-wgint7l4qhqkps83.us.auth0.com/api/v2/");
        requestBody.put("grant_type", config.getGrantType());

        HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> result = restTemplate.postForObject(AUTH0_TOKEN_URL, request, HashMap.class);

        return result.get("access_token");
    }
}

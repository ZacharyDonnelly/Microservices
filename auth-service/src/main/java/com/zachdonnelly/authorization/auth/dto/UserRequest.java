package com.zachdonnelly.authorization.auth.dto;

public record UserRequest(String customerId, String role, String password, String firstName, String lastName, String email, String address1, String address2, String city, String state, Integer zip) {
}

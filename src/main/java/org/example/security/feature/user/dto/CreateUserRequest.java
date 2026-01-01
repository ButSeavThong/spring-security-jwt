package org.example.security.feature.user.dto;


public record CreateUserRequest(
        String userName,
        String email,
        String password,
        String firstName,
        String lastName

) {}

package org.example.security.feature.user.dto;

public record UserResponse (
        String username,
        String email,
        String firstName,
        String lastName
) {
}

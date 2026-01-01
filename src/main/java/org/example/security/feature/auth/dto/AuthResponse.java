package org.example.security.feature.auth.dto;

public record AuthResponse(
        String type,
        String accessToken,
        String refreshToken
) {
}

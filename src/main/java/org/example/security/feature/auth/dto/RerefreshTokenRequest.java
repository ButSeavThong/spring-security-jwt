package org.example.security.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RerefreshTokenRequest(
        @NotBlank(message = "RefreshToken is Required !")
        String refreshToken
) {
}

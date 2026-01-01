package org.example.security.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "UserName is Required !")
        String userName,
        @NotBlank(message = "Password Must include !")
        String password

) {}

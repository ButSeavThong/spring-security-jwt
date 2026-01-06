package org.example.security.feature.auth;

import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;
import org.example.security.feature.auth.dto.RerefreshTokenRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refresh(RerefreshTokenRequest rerefreshTokenRequest);
}

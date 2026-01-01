package org.example.security.feature.auth;

import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);
}

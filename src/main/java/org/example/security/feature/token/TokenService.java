package org.example.security.feature.token;

import org.example.security.feature.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public interface TokenService {

    AuthResponse createToken(Authentication auth);

    String createAccessToken(Authentication auth);

    String  createRefreshToken(Authentication auth);
}



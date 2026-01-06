package org.example.security.feature.token;

import org.example.security.feature.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public class TokenServiceImpl implements TokenService {
    private final String TOKEN_TYPE="Bearer";
    @Override
    public AuthResponse createToken(Authentication auth) {
        return new AuthResponse(
                TOKEN_TYPE,
                createAccessToken(auth),
                createRefreshToken(auth)
        );
    }

    @Override
    public String createAccessToken(Authentication auth) {
        return null;
    }

    @Override
    public String createRefreshToken(Authentication auth) {
        return null;
    }
}

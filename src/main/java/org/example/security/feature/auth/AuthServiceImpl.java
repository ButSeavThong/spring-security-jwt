package org.example.security.feature.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;
import org.example.security.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    // inject Dao(authentication main )
    private final DaoAuthenticationProvider authProvider;
    private final JwtEncoder jwtEncoder;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password());
        authentication =  authProvider.authenticate(authentication);
        log.debug("Logging Authentication token: {} ",authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.debug("User Details: {}", userDetails.getUsername());
        log.debug("User Details: {}", userDetails.getUser().getUserName());
        log.debug("User Details: {}", userDetails.getAuthorities());

        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUser().getId().toString())
                .subject("Access Resource")
                .audience(List.of("Web Application","Mobile Application"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer(loginRequest.userName())
                .build();

         String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return new AuthResponse("Bearer",
                accessToken,
                ""
        );
    }
}

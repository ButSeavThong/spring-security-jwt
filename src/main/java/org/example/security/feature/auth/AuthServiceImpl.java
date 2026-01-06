package org.example.security.feature.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;
import org.example.security.feature.auth.dto.RerefreshTokenRequest;
import org.example.security.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    // inject Dao(authentication main )
    private final DaoAuthenticationProvider authProvider;
    private final JwtAuthenticationProvider  jwtAuthenticationProvider;
    // accessToken endcoder
    private final JwtEncoder jwtEncoder;

    // refreshToken Encoder
    private JwtEncoder refreshJwtEncoder;
    @Qualifier("refreshJwtEncoder")
    @Autowired
    public void setRefreshJwtEncoder(JwtEncoder refreshJwtEncoder) {
        this.refreshJwtEncoder = refreshJwtEncoder ;
    }


    @Override
    public AuthResponse refresh(RerefreshTokenRequest refreshTokenRequest) {
        Authentication authentication = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        authentication =  jwtAuthenticationProvider.authenticate(authentication);
        log.debug("Refresh Authentication token: {} ",authentication);
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet accessTokenJwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .id(jwt.getId())
                .audience(List.of("Web","Mobile"))
                .issuer(jwt.getId())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenJwtClaimsSet)).getTokenValue();
        return new AuthResponse("Bearer",accessToken, refreshTokenRequest.refreshToken());

        // old refresh token return to ( if have database store later
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password());
        authentication =  authProvider.authenticate(authentication);

        // user details
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // add scope to payload of jwt token
        String scope =  userDetails.getAuthorities()
                .stream()
                // this case not include role
                .filter(grantedAuthority -> !grantedAuthority.getAuthority().startsWith("ROLE_"))
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUser().getId().toString())
                .subject("Access Resource")
                .audience(List.of("Web Application","Mobile Application"))
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .issuer("Resource Owner Via NextJs"+ loginRequest.userName())
                .claim("scope", scope)
                .build();

        JwtClaimsSet refreshJwtClaimsSet = JwtClaimsSet.builder()
                .id(userDetails.getUser().getId().toString())
                .subject("New Access_Token")
                .audience(List.of("Web Application","Mobile Application"))
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .issuer(loginRequest.userName())
                .claim("scope", scope)
                .build();

         String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
         String refreshToken = refreshJwtEncoder.encode(JwtEncoderParameters.from(refreshJwtClaimsSet)).getTokenValue();
        return new AuthResponse
                ("Bearer", accessToken, refreshToken);
    }


}

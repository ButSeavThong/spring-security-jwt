package org.example.security.feature.auth;


import lombok.RequiredArgsConstructor;
import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// allow client issue for token ( authentication and authorization here )
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public AuthResponse login( @RequestBody  LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}

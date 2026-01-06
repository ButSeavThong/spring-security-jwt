package org.example.security.feature.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.security.feature.auth.dto.AuthResponse;
import org.example.security.feature.auth.dto.LoginRequest;
import org.example.security.feature.auth.dto.RerefreshTokenRequest;
import org.example.security.feature.user.UserService;
import org.example.security.feature.user.dto.CreateUserRequest;
import org.example.security.feature.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

// allow client issue for token ( authentication and authorization here )
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public AuthResponse login( @RequestBody  LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken( @Valid @RequestBody RerefreshTokenRequest rerefreshTokenRequest) {
        return authService.refresh(rerefreshTokenRequest);
    }


}

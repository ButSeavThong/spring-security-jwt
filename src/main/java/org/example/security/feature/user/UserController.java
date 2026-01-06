package org.example.security.feature.user;

import lombok.RequiredArgsConstructor;
import org.example.security.feature.user.dto.CreateUserRequest;
import org.example.security.feature.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_user:read')")
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }


    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserResponse findUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

}

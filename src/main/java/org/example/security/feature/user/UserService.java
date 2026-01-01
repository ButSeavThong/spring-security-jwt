package org.example.security.feature.user;

import org.example.security.feature.user.dto.CreateUserRequest;
import org.example.security.feature.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    List<UserResponse> findAllUsers();
}

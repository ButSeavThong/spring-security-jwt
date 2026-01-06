package org.example.security.feature.user;

import lombok.RequiredArgsConstructor;
import org.example.security.feature.roleAndAuthority.RoleRepository;
import org.example.security.feature.user.dto.CreateUserRequest;
import org.example.security.feature.user.dto.UserResponse;
import org.example.security.model.Role;
import org.example.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest req) {
        if(userRepository.existsByUserName(req.userName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if(req.userName()== null || req.userName().isEmpty()){
            throw new IllegalArgumentException("userName cannot be null or empty");
        }
        if(req.email()== null || req.email().isEmpty()){
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if(req.password()== null || req.password().isEmpty()){
            throw new IllegalArgumentException("password cannot be null or empty");
        }

        User user = new User();
        user.setUserName(req.userName());
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setEnabled(true);

        // assign role
        List<Role> roles = new ArrayList<>();
        String userRole ="USER";
        Role role = roleRepository.findByRoleName(userRole)
                .orElseThrow(()-> new IllegalArgumentException("Role not found") );
        roles.add(role);
        user.setRoles(roles);

        user.setAccounts(null);

        // custome fields
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        user = userRepository.save(user);

        return new  UserResponse(
                user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user->new UserResponse(user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
                )).collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(
                user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

}

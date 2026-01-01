package org.example.security.utils;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.security.feature.role.RoleRepository;
import org.example.security.model.Role;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleInitialization {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if(roleRepository.count() == 0) {
            Set<Role> roles = new HashSet<>();

            Role userRole = new Role();
            userRole.setRoleName("USER");
            userRole.setDescription("User Role");
            userRole.setDeleted(false);

            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            roleAdmin.setDescription("Admin Role");
            roleAdmin.setDeleted(false);

            // save all roles to Set()
            roles.add(userRole);
            roles.add(roleAdmin);

            roleRepository.saveAll(roles);
        }


    }
}

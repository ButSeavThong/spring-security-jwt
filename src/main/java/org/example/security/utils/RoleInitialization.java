package org.example.security.utils;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.security.feature.roleAndAuthority.RoleRepository;
import org.example.security.model.Authority;
import org.example.security.model.Role;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleInitialization {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if(roleRepository.count() == 0) {
            Set<Role> roles = new HashSet<>();

            // collection of authorities
            Authority userRead = new Authority();
            userRead.setName("user:read");

            Authority userWrite = new Authority();
            userWrite.setName("user:write");

            Authority transactionRead = new Authority();
            transactionRead.setName("transaction:read");

            Authority transactionWrite = new Authority();
            transactionWrite.setName("transaction:write");

            Authority accountRead    = new Authority();
            accountRead.setName("account:read");

            Authority accountWrite = new Authority();
            accountWrite.setName("account:write");

            Authority accountTypeRead = new Authority();
            accountTypeRead.setName("account:type:read");

            Authority accountTypeWrite = new Authority();
            accountTypeWrite.setName("account:type:write");

            Role userRole = new Role();
            userRole.setRoleName("USER");
            userRole.setDescription("User Role");
            userRole.setDeleted(false);
            userRole.setAuthorities(List.of(userRead,accountRead, accountTypeRead,transactionRead));


            Role customerRole = new Role();
            customerRole.setRoleName("CUSTOMER");
            customerRole.setDescription("Customer Role");
            customerRole.setDeleted(false);
            customerRole.setAuthorities(List.of(userWrite,accountWrite,transactionWrite));


            Role staffRole = new Role();
            staffRole.setRoleName("STAFF");
            staffRole.setDescription("Staff Role");
            staffRole.setDeleted(false);
            staffRole.setAuthorities(List.of(accountTypeWrite));

            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            roleAdmin.setDescription("Admin Role");
            roleAdmin.setDeleted(false);
            roleAdmin.setAuthorities(List.of(userWrite));

            // save all roles to Set()
            roles.add(userRole);
            roles.add(customerRole);
            roles.add(staffRole);
            roles.add(roleAdmin);

            roleRepository.saveAll(roles);
        }

    }
}

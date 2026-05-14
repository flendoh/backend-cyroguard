package com.example.cryoguard.iam.infrastructure.persistence.seed;

import com.example.cryoguard.iam.domain.model.entities.Role;
import com.example.cryoguard.iam.domain.model.valueobjects.Roles;
import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.cryoguard.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.cryoguard.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data seeder that inserts default roles and an admin user if they do not exist.
 * This runs on application startup.
 */
@Component
@Profile("!test")
@Transactional
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptHashingService hashingService;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, BCryptHashingService hashingService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public void run(String... args) {
        // Ensure roles exist
        ensureRole(Roles.ROLE_ADMINISTRATOR);
        ensureRole(Roles.ROLE_OPERATOR);

        // Ensure admin user exists
        if (!userRepository.existsByUsername("admin")) {
            var adminRole = roleRepository.findByName(Roles.ROLE_ADMINISTRATOR).orElseGet(() -> roleRepository.save(new Role(Roles.ROLE_ADMINISTRATOR)));
            var encoded = hashingService.encode("admin");
            var admin = new User("admin", encoded, "admin@cryoguard.com", List.of(adminRole));
            userRepository.save(admin);
        }
    }

    private void ensureRole(Roles role) {
        if (!roleRepository.existsByName(role)) {
            roleRepository.save(new Role(role));
        }
    }
}
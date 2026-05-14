package com.example.cryoguard.iam.interfaces.rest.transform;

import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.domain.model.entities.Role;
import com.example.cryoguard.iam.interfaces.rest.resources.UserResource;

import java.util.List;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        // Get the primary role (first one) as lowercase string
        String role = user.getRoles().stream()
                .map(Role::getStringName)
                .findFirst()
                .orElse("operator");

        // Convert status to lowercase string
        String status = user.getStatus().name().toLowerCase();

        return new UserResource(
            user.getId(),
            user.getUsername(), // name field maps to username
            user.getEmail(),
            role,
            status,
            user.getLastLogin(),
            user.getCreatedAt()
        );
    }
}
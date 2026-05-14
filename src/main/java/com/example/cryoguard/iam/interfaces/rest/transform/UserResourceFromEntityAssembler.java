package com.example.cryoguard.iam.interfaces.rest.transform;

import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.domain.model.entities.Role;
import com.example.cryoguard.iam.interfaces.rest.resources.UserResource;

import java.util.List;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();
        return new UserResource(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            roles,
            user.getStatus(),
            user.getLastLogin()
        );
    }
}
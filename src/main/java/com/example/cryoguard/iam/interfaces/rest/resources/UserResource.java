package com.example.cryoguard.iam.interfaces.rest.resources;

import com.example.cryoguard.iam.domain.model.aggregates.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User resource
 * <p>
 *     This record represents the resource for a user in responses.
 * </p>
 * @param id the user id
 * @param username the username
 * @param email the email
 * @param roles the roles
 * @param status the status (ACTIVE or INACTIVE)
 * @param lastLogin the last login timestamp
 */
public record UserResource(
    Long id,
    String username,
    String email,
    List<String> roles,
    User.UserStatus status,
    LocalDateTime lastLogin
) {
}
package com.example.cryoguard.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Authenticated user resource
 * <p>
 *     This record represents the resource for an authenticated user response.
 * </p>
 * @param id the user id
 * @param username the username
 * @param email the email
 * @param token the JWT token
 * @param roles the roles
 */
public record AuthenticatedUserResource(
    Long id,
    String username,
    String email,
    String token,
    List<String> roles
) {
}
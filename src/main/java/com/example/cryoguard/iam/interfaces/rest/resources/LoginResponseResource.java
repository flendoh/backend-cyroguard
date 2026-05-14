package com.example.cryoguard.iam.interfaces.rest.resources;

/**
 * Login response resource
 * <p>
 *     This record represents the response for a successful login.
 *     Maps to frontend LoginResponse interface.
 * </p>
 * @param token the JWT token
 * @param user the user info (id, name, email, role)
 */
public record LoginResponseResource(
    String token,
    UserInfoResource user
) {
    /**
     * User info nested in login response
     */
    public record UserInfoResource(
        Long id,
        String name,
        String email,
        String role
    ) {
    }
}
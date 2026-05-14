package com.example.cryoguard.iam.interfaces.rest.resources;

/**
 * Sign in resource (login request)
 * <p>
 *     Request body for POST /auth/login
 * </p>
 * @param email the user email
 * @param password the user password
 */
public record SignInResource(String email, String password) {
}
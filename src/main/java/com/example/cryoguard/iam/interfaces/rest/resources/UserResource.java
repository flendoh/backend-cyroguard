package com.example.cryoguard.iam.interfaces.rest.resources;

import java.util.Date;

/**
 * User resource
 * <p>
 *     This record represents the resource for a user in responses.
 *     All strings are lowercase as per frontend expectations.
 * </p>
 * @param id the user id
 * @param name the user's full name
 * @param email the user's email
 * @param role the user's role (lowercase: admin, operator, supervisor, ngo)
 * @param status the user's status (lowercase: active, inactive, locked)
 * @param lastLogin the last login timestamp
 * @param createdAt the creation timestamp
 */
public record UserResource(
    Long id,
    String name,
    String email,
    String role,
    String status,
    Date lastLogin,
    Date createdAt
) {
}
package com.example.cryoguard.iam.domain.model.queries;

/**
 * Get all users query
 * <p>
 *     This class represents the query to get all the users in the system with optional filtering.
 * </p>
 * @param search search term for name or email (optional)
 * @param role filter by role (optional, lowercase)
 * @param status filter by status (optional, lowercase)
 * @param page page number (0-indexed)
 * @param size page size
 */
public record GetAllUsersQuery(
    String search,
    String role,
    String status,
    int page,
    int size
) {
    public GetAllUsersQuery() {
        this(null, null, null, 0, 20);
    }

    public GetAllUsersQuery(String search, String role, String status) {
        this(search, role, status, 0, 20);
    }
}
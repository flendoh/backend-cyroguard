package com.example.cryoguard.iam.application.internal.queryservices;

import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.domain.model.queries.GetAllUsersQuery;
import com.example.cryoguard.iam.domain.model.queries.GetUserByIdQuery;
import com.example.cryoguard.iam.domain.model.queries.GetUserByUsernameQuery;
import com.example.cryoguard.iam.domain.services.UserQueryService;
import com.example.cryoguard.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository {@link UserRepository} instance.
     */
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is used to handle {@link GetAllUsersQuery} query with pagination and filtering.
     * @param query {@link GetAllUsersQuery} instance.
     * @return {@link Page} of {@link User} instances.
     * @see GetAllUsersQuery
     */
    @Override
    public Page<User> handle(GetAllUsersQuery query) {
        Pageable pageable = PageRequest.of(query.page(), query.size());

        // Get all users and apply filters in memory
        List<User> allUsers = userRepository.findAll();

        // Filter by search term (name or email)
        if (query.search() != null && !query.search().isBlank()) {
            String searchLower = query.search().toLowerCase();
            allUsers = allUsers.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(searchLower)
                            || user.getEmail().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        // Filter by role
        if (query.role() != null && !query.role().isBlank()) {
            String roleName = "ROLE_" + query.role().toUpperCase();
            allUsers = allUsers.stream()
                    .filter(user -> user.getRoles().stream()
                            .anyMatch(role -> role.getName().name().equals(roleName)))
                    .collect(Collectors.toList());
        }

        // Filter by status
        if (query.status() != null && !query.status().isBlank()) {
            String statusName = query.status().toUpperCase();
            allUsers = allUsers.stream()
                    .filter(user -> user.getStatus().name().equals(statusName))
                    .collect(Collectors.toList());
        }

        // Manual pagination since we're filtering in memory
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allUsers.size());

        List<User> pageContent = start < allUsers.size()
                ? allUsers.subList(start, end)
                : List.of();

        return new org.springframework.data.domain.PageImpl<>(pageContent, pageable, allUsers.size());
    }

    /**
     * This method is used to handle {@link GetUserByIdQuery} query.
     * @param query {@link GetUserByIdQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByIdQuery
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * This method is used to handle {@link GetUserByUsernameQuery} query.
     * @param query {@link GetUserByUsernameQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByUsernameQuery
     */
    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}
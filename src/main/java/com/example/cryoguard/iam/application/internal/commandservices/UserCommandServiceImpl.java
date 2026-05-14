package com.example.cryoguard.iam.application.internal.commandservices;

import com.example.cryoguard.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.cryoguard.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.domain.model.commands.SignInCommand;
import com.example.cryoguard.iam.domain.model.commands.SignUpCommand;
import com.example.cryoguard.iam.domain.model.commands.UpdateUserCommand;
import com.example.cryoguard.iam.domain.model.commands.DisableUserCommand;
import com.example.cryoguard.iam.domain.services.UserCommandService;
import com.example.cryoguard.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.cryoguard.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand}, {@link SignUpCommand}, {@link UpdateUserCommand}, and {@link DisableUserCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the email and password
     * @return and optional containing the user matching the email and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmailIgnoreCase(command.email());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (user.get().isLocked())
            throw new LockedUserException("User account is locked");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");

        // Record last login
        user.get().recordLogin();
        userRepository.save(user.get());

        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Exception for locked user accounts
     */
    public static class LockedUserException extends RuntimeException {
        public LockedUserException(String message) {
            super(message);
        }
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username, email, and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");

        var role = (command.role() == null)
            ? roleRepository.findByName(com.example.cryoguard.iam.domain.model.valueobjects.Roles.ROLE_OPERATOR).orElseThrow(() -> new RuntimeException("Role not found"))
            : roleRepository.findByName(command.role().getName()).orElseThrow(() -> new RuntimeException("Role not found: " + command.role().getName()));

        var user = new User(command.username(), hashingService.encode(command.password()), command.email(), List.of(role));
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    /**
     * Handle the update user command
     * <p>
     *     This method handles the {@link UpdateUserCommand} command and returns the updated user.
     * </p>
     * @param command the update user command
     * @return the updated user
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var userOpt = userRepository.findById(command.userId());
        if (userOpt.isEmpty())
            throw new RuntimeException("User not found");

        var user = userOpt.get();

        // Update fields
        if (command.username() != null && !command.username().isBlank()) {
            user.setUsername(command.username());
        }
        if (command.email() != null && !command.email().isBlank()) {
            user.setEmail(command.email());
        }
        if (command.status() != null) {
            user.setStatus(command.status());
        }
        if (command.role() != null && !command.role().isBlank()) {
            var role = roleRepository.findByName(com.example.cryoguard.iam.domain.model.valueobjects.Roles.valueOf(command.role().trim().toUpperCase()))
                .orElseThrow(() -> new RuntimeException("Role not found: " + command.role()));
            user.setRoles(new java.util.HashSet<>(List.of(role)));
        }

        userRepository.save(user);
        return Optional.of(user);
    }

    /**
     * Handle the disable user command
     * <p>
     *     This method handles the {@link DisableUserCommand} command.
     * </p>
     * @param command the disable user command
     */
    @Override
    public void handle(DisableUserCommand command) {
        var userOpt = userRepository.findById(command.userId());
        if (userOpt.isEmpty())
            throw new RuntimeException("User not found");
            
        var user = userOpt.get();
        user.disable();
        userRepository.save(user);
    }
}
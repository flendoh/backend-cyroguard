package com.example.cryoguard.iam.interfaces.rest;

import com.example.cryoguard.iam.domain.model.aggregates.User;
import com.example.cryoguard.iam.domain.model.commands.UpdateUserCommand;
import com.example.cryoguard.iam.domain.model.queries.GetAllUsersQuery;
import com.example.cryoguard.iam.domain.model.queries.GetUserByIdQuery;
import com.example.cryoguard.iam.domain.services.UserCommandService;
import com.example.cryoguard.iam.domain.services.UserQueryService;
import com.example.cryoguard.iam.interfaces.rest.resources.UpdateUserResource;
import com.example.cryoguard.iam.interfaces.rest.resources.UserResource;
import com.example.cryoguard.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.example.cryoguard.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UsersController
 * <p>
 *     This controller is responsible for handling user management requests.
 *     It exposes CRUD endpoints for users.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Get all users
     * @return list of all users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * Get user by id
     * @param userId the user id
     * @return the user resource
     */
    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get user by id", description = "Get the user with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Update user
     * @param userId the user id
     * @param updateUserResource the update request
     * @return the updated user resource
     */
    @PutMapping(value = "/{userId}")
    @Operation(summary = "Update user", description = "Update the user with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<UserResource> updateUser(@PathVariable Long userId, @RequestBody UpdateUserResource updateUserResource) {
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, updateUserResource);
        var updatedUser = userCommandService.handle(updateUserCommand);
        if (updatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Disable user
     * @param userId the user id
     * @return no content
     */
    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Disable user", description = "Disable the user with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User disabled successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")})
    public ResponseEntity<Void> disableUser(@PathVariable Long userId) {
        var disableUserCommand = new com.example.cryoguard.iam.domain.model.commands.DisableUserCommand(userId);
        userCommandService.handle(disableUserCommand);
        return ResponseEntity.noContent().build();
    }
}
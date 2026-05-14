package com.example.cryoguard.iam.interfaces.rest;

import com.example.cryoguard.iam.application.internal.commandservices.UserCommandServiceImpl;
import com.example.cryoguard.iam.domain.services.UserCommandService;
import com.example.cryoguard.iam.interfaces.rest.resources.LoginResponseResource;
import com.example.cryoguard.iam.interfaces.rest.resources.SignInResource;
import com.example.cryoguard.iam.interfaces.rest.resources.SignUpResource;
import com.example.cryoguard.iam.interfaces.rest.resources.UserResource;
import com.example.cryoguard.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.example.cryoguard.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.example.cryoguard.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/login</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the login request (POST /auth/login).
     * @param signInResource the sign-in request body with email and password.
     * @return the login response with JWT token and user info.
     */
    @PostMapping(value = "/login", consumes = {"application/json"})
    @Operation(summary = "Login", description = "Authenticate with email and password to receive JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful."),
            @ApiResponse(responseCode = "401", description = "Invalid credentials."),
            @ApiResponse(responseCode = "403", description = "Account locked.")})
    public ResponseEntity<LoginResponseResource> login(@RequestBody SignInResource signInResource) {
        try {
            var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
            var result = userCommandService.handle(signInCommand);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            var user = result.get().getLeft();
            var token = result.get().getRight();

            // Get primary role as lowercase string
            String role = user.getRoles().stream()
                    .map(r -> {
                        String name = r.getName().name();
                        if (name.startsWith("ROLE_")) {
                            name = name.substring(5);
                        }
                        return name.toLowerCase();
                    })
                    .findFirst()
                    .orElse("operator");

            var loginResponse = new LoginResponseResource(
                token,
                new LoginResponseResource.UserInfoResource(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    role
                )
            );
            return ResponseEntity.ok(loginResponse);
        } catch (UserCommandServiceImpl.LockedUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping(value = "/sign-up", consumes = {"application/json"})
    @Operation(summary = "Sign-up", description = "Sign-up with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request.")})
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
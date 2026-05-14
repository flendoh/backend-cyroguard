# Design: IAM Bounded Context

## Technical Approach

IAM bounded context handles authentication and user management for CryoGuard. It exposes REST endpoints consumed by the React frontend, uses JWT for stateless authentication, and follows the existing layered architecture (domain → application → infrastructure → presentation).

## Package Structure

```
com.example.cryoguard.iam
├── domain
│   ├── model
│   │   ├── aggregates/
│   │   │   └── User.java           # User entity (aggregate root)
│   │   ├── entities/
│   │   │   └── Role.java           # Role entity
│   │   ├── valueobjects/
│   │   │   └── Roles.java          # Roles enum (ADMIN, OPERATOR, SUPERVISOR, NGO)
│   │   ├── commands/               # Command objects
│   │   └── queries/                # Query objects
│   └── services/
│       ├── UserCommandService.java
│       └── UserQueryService.java
├── application
│   └── internal
│       ├── commandservices/
│       │   └── UserCommandServiceImpl.java
│       └── queryservices/
│           └── UserQueryServiceImpl.java
├── infrastructure
│   ├── persistence
│   │   └── jpa/repositories/
│   │       ├── UserRepository.java
│   │       └── RoleRepository.java
│   ├── hashing/bcrypt/
│   │   └── BCryptHashingService.java
│   ├── tokens/jwt/
│   │   ├── BearerTokenService.java
│   │   └── services/TokenServiceImpl.java
│   └── authorization/sfs/
│       ├── configuration/
│       │   └── WebSecurityConfiguration.java
│       ├── pipeline/
│       │   ├── BearerAuthorizationRequestFilter.java
│       │   └── UnauthorizedRequestHandlerEntryPoint.java
│       └── services/
│           └── UserDetailsServiceImpl.java
└── presentation
    ├── controllers/
    │   └── AuthController.java
    ├── resources/
    │   ├── LoginResource.java
    │   ├── LoginResponseResource.java
    │   ├── UserResource.java
    │   └── CreateUserResource.java
    └── assemblers/
        └── UserResourceAssembler.java
```

## Entity Design

### User.java (existing → modifications needed)

```java
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 50)
    @Column(unique = true, nullable = false)
    private String username;  // maps to 'name' in frontend

    @NotBlank @Size(max = 120)
    @Column(nullable = false)
    private String password;  // NEVER returned in responses (bcrypt hashed)

    @NotBlank @Size(max = 255)
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserStatus status = UserStatus.ACTIVE;  // active|inactive|locked (lowercase)

    @Column
    private LocalDateTime lastLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", ...)
    private Set<Role> roles;  // admin, operator, supervisor, ngo

    // AuditableAbstractAggregateRoot provides:
    // - createdAt, updatedAt timestamps
    // - deletedAt for soft delete (IAM-05)
}
```

### Role.java (existing → extend Roles enum)

```java
// Extend Roles.java enum to add missing roles:
public enum Roles {
    ROLE_ADMINISTRATOR,  // maps to 'admin' (lowercase)
    ROLE_OPERATOR,      // maps to 'operator' (lowercase)
    ROLE_SUPERVISOR,    // maps to 'supervisor' (lowercase)
    ROLE_NGO            // maps to 'ngo' (lowercase)
}
```

### UserStatus enum (add locked status)

```java
public enum UserStatus {
    ACTIVE,   // 'active' in API
    INACTIVE, // 'inactive' in API
    LOCKED    // 'locked' in API
}
```

## API Endpoints

| Method | Path | Description | Auth Required | Roles |
|--------|------|-------------|---------------|-------|
| POST | /auth/login | Authenticate user, return JWT | No | - |
| GET | /users | List users (paginated, filterable) | Yes | ADMIN, SUPERVISOR |
| POST | /users | Create new user | Yes | ADMIN |
| PUT | /users/{id} | Update user role/status | Yes | ADMIN |
| DELETE | /users/{id} | Soft delete user | Yes | ADMIN |

## Architecture Decisions

### Decision: Use soft delete with `deletedAt` timestamp

**Choice**: Extend `AuditableAbstractAggregateRoot` for soft deletes
**Alternatives considered**: Hard delete, separate `isDeleted` boolean column
**Rationale**: AuditableAbstractAggregateRoot already provides `deletedAt` field. Soft delete preserves audit trail and allows future recovery (IAM-05).

### Decision: JWT token format

**Choice**: Store username in JWT subject, configurable expiration (default 24h)
**Alternatives considered**: Store full user object in JWT claims
**Rationale**: JWT already implemented via `TokenServiceImpl`. Storing username keeps tokens small; user data fetched from DB on each request (IAM-09, IAM-14).

### Decision: Roles as lowercase strings in API

**Choice**: Store roles internally as `ROLE_ADMINISTRATOR` enum, return lowercase string in API
**Alternatives considered**: Store roles as lowercase strings directly
**Rationale**: Matches frontend expectation `role: 'admin'` while keeping internal Java conventions (IAM-01).

## Data Flow

```
[Frontend] ──POST /auth/login──> [AuthController]
                                    │
                                    ▼
                         [UserCommandService.handle(SignInCommand)]
                                    │
                                    ▼
                         [UserRepository.findByEmail(email)]
                                    │
                         ┌──────────┴──────────┐
                         ▼                     ▼
                   [Valid credentials]    [Invalid]
                         │                     │
                         ▼                     ▼
            [HashingService.check(password)]  [Return 401]
                         │
                    [Password matches]
                         │
                         ▼
            [TokenServiceImpl.generateToken(username)]
                         │
                         ▼
            [Return LoginResponseResource(token, user)]
```

## DTO Mapping

### LoginResponseResource (maps to frontend LoginResponse)

```java
public class LoginResponseResource {
    private String token;           // JWT
    private UserInfoResource user;  // { id, name, email, role }
}
```

### UserResource (maps to frontend User, password never included)

```java
public class UserResource {
    private String id;           // UUID string
    private String name;        // username
    private String email;
    private String role;        // lowercase: admin|operator|supervisor|ngo
    private String status;       // lowercase: active|inactive|locked
    private String lastLogin;   // ISO timestamp
    private String createdAt;   // ISO timestamp
}
```

## Security Configuration

- `WebSecurityConfiguration`: Permit `/auth/login`, block all other endpoints
- `BearerAuthorizationRequestFilter`: Extract JWT from `Authorization: Bearer <token>` header
- JWT validation via `TokenServiceImpl.validateToken()`
- Password hashing via BCrypt (already implemented)

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `iam/domain/model/valueobjects/Roles.java` | Modify | Add SUPERVISOR, NGO roles |
| `iam/domain/model/aggregates/User.java` | Modify | Add LOCKED status, ensure soft delete works |
| `iam/presentation/controllers/AuthController.java` | Create | POST /auth/login endpoint |
| `iam/presentation/resources/LoginResource.java` | Create | Request DTO for login |
| `iam/presentation/resources/LoginResponseResource.java` | Create | Response with token + user |
| `iam/presentation/resources/UserResource.java` | Create | User response DTO |
| `iam/presentation/assemblers/UserResourceAssembler.java` | Create | Entity ↔ Resource mapper |
| `iam/domain/services/UserQueryService.java` | Modify | Add findByEmail, findById methods |
| `iam/infrastructure/persistence/jpa/repositories/UserRepository.java` | Modify | Add findByEmailIgnoreCase |

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | UserCommandService.signIn() | Mock UserRepository, verify token generation |
| Unit | Password hashing | Mock HashingService, verify BCrypt called |
| Integration | POST /auth/login | Test valid/invalid credentials, locked account |
| Integration | GET /users | Test pagination, role filtering, search |
| E2E | Full auth flow | Login → get token → use token for /users |

## Open Questions

- [ ] Should JWT expiration be configurable per role? (Admin longer than NGO)
- [ ] Do we need refresh tokens, or is 24h JWT sufficient?
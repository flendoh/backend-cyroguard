# Implementation Tasks: IAM + Monitoring Bounded Contexts

## IAM Bounded Context

### Infrastructure

- [x] 1.1 Review and update User entity (add LOCKED status to UserStatus enum)
  - File: `src/main/java/com/example/cryoguard/iam/domain/model/aggregates/User.java`
  - Current: `UserStatus` has `ACTIVE, INACTIVE`
  - Need: Add `LOCKED` value; update `status` field to return lowercase string in API
  - Note: User extends `AuditableAbstractAggregateRoot` (soft delete via `deletedAt` already provided)

- [x] 1.2 Review UserRepository
  - File: `src/main/java/com/example/cryoguard/iam/infrastructure/persistence/jpa/repositories/UserRepository.java`
  - Already has: `findByEmail`, `findByUsername`, `existsByEmail`, `existsByUsername`
  - Consider adding: `findByEmailIgnoreCase` for case-insensitive login

- [x] 1.3 Update Roles enum (add SUPERVISOR, NGO roles)
  - File: `src/main/java/com/example/cryoguard/iam/domain/model/valueobjects/Roles.java`
  - Current: `ROLE_ADMINISTRATOR, ROLE_OPERATOR`
  - Need: Add `ROLE_SUPERVISOR, ROLE_NGO`
  - Note: Frontend expects lowercase string mapping (`'admin'`, `'operator'`, `'supervisor'`, `'ngo'`)

- [x] 1.4 Review Role entity
  - File: `src/main/java/com/example/cryoguard/iam/domain/model/entities/Role.java`
  - Verify `getStringName()` returns lowercase role string for API responses

### Application Services

- [x] 2.1 Review UserCommandService
  - File: `src/main/java/com/example/cryoguard/iam/domain/services/UserCommandService.java`
  - Already exists with `handle(SignInCommand)`, `handle(SignUpCommand)`, `handle(UpdateUserCommand)`

- [x] 2.2 Review UserQueryService
  - File: `src/main/java/com/example/cryoguard/iam/domain/services/UserQueryService.java`
  - Already has `findByEmail`, `findById`, `findAll` methods

### Controllers

- [x] 3.1 Review/Update AuthController (POST /auth/login)
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/AuthenticationController.java`
  - Current: Endpoint is `/api/v1/auth/sign-in`
  - Spec requires: POST `/api/v1/auth/login`
  - Actions:
    - Add `/login` endpoint OR rename `/sign-in` to `/login`
    - Ensure response includes `LoginResponseResource` with token + user info
    - Handle locked accounts (return 403)
    - Handle invalid credentials (return 401)

- [x] 3.2 Review UsersController (CRUD endpoints)
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/UsersController.java`
  - Already has: GET `/users`, GET `/users/{id}`, PUT `/users/{id}`, DELETE `/users/{id}`
  - Needs verification:
    - Pagination support (page, size params)
    - Search by name/email
    - Role and status filtering
    - Returns lowercase role/status strings in response

- [x] 3.3 Add SignUp endpoint if missing (POST /users)
  - Currently AuthenticationController has `/sign-up` but spec wants `/users` POST for creation
  - Verify: POST `/api/v1/users` creates new user (ADMIN only)

### DTOs and Assemblers

- [x] 4.1 Add LoginRequest DTO (POST /auth/login body)
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/resources/SignInResource.java` (exists)
  - Verify it has: `email`, `password` fields

- [x] 4.2 Add LoginResponse DTO
  - Create: `src/main/java/com/example/cryoguard/iam/interfaces/rest/resources/LoginResponseResource.java`
  - Needs: `token` (JWT string), `user` (nested object with id, name, email, role)
  - Frontend expects lowercase role string

- [x] 4.3 Review UserResource for API responses
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/resources/UserResource.java`
  - Currently returns: `id, username, email, roles (List<String>), status (enum), lastLogin`
  - Need: Ensure `status` returns lowercase string (`'active'|'inactive'|'locked'`), not `ACTIVE`
  - Need: Ensure `role` returns lowercase string (`'admin'|'operator'|'supervisor'|'ngo'`)

- [x] 4.4 Review UserResourceFromEntityAssembler
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/transform/UserResourceFromEntityAssembler.java`
  - Uses `Role::getStringName` - verify this returns lowercase

- [x] 4.5 Review AuthenticatedUserResource
  - File: `src/main/java/com/example/cryoguard/iam/interfaces/rest/resources/AuthenticatedUserResource.java`
  - Used in login response; verify it matches frontend `LoginResponse` interface:
    - `token: string`
    - `user: { id, name, email, role }`

### Security Configuration

- [x] 5.1 Review WebSecurityConfiguration
  - File: `src/main/java/com/example/cryoguard/iam/infrastructure/authorization/sfs/configuration/WebSecurityConfiguration.java`
  - Should permit `/auth/login`, block all other endpoints

- [x] 5.2 Review BearerAuthorizationRequestFilter
  - File: `src/main/java/com/example/cryoguard/iam/infrastructure/authorization/sfs/pipeline/BearerAuthorizationRequestFilter.java`
  - JWT extraction from `Authorization: Bearer <token>` header

- [x] 5.3 Review TokenServiceImpl
  - File: `src/main/java/com/example/cryoguard/iam/infrastructure/tokens/jwt/services/TokenServiceImpl.java`
  - Already implements JWT generation and validation

---

## Monitoring Bounded Context

### Infrastructure

- [x] 1.1 Review and update Container entity
  - File: `src/main/java/com/example/cryoguard/monitoring/domain/aggregates/Container.java`
  - Status values: `ACTIVE, INACTIVE, MAINTENANCE` (per spec)
  - Added fields per spec:
    - `productType` (String) - "vaccines", "insulin", "blood"
    - `operatorId` (Long) - assigned operator
    - `lastUpdate` (LocalDateTime) - maps to `lastUpdate` in frontend
    - `currentLocation` - GpsCoordinates embeddable
  - `batteryLevel` exists, `lastSync` replaced with `lastUpdate`

- [x] 1.2 Replace ContainerStatus enum
  - File: `src/main/java/com/example/cryoguard/monitoring/domain/valueobjects/ContainerStatus.java`
  - Current: `ACTIVE, INACTIVE, MAINTENANCE`
  - These are lowercase strings in API responses

- [x] 1.3 Review ContainerRepository
  - File: `src/main/java/com/example/cryoguard/monitoring/infrastructure/persistence/ContainerRepository.java`
  - Added: `findByStatus()`, `findByProductType()`, `findByStatusAndProductType()`, `findAllActive()` with pagination

- [x] 1.4 Create Event entity (if not exists)
  - New file: `src/main/java/com/example/cryoguard/monitoring/domain/entities/Event.java`
  - Fields: `id`, `containerId` (Long), `type` (EventType enum), `severity` (EventSeverity enum), `message`, `timestamp`, `acknowledged`, `acknowledgedBy`, `acknowledgedAt`
  - EventType: `TEMPERATURE, HUMIDITY, VIBRATION, DOOR, GEOFENCE, BATTERY, OFFLINE` (lowercase in API)
  - EventSeverity: `CRITICAL, WARNING, INFO` (lowercase in API)

- [x] 1.5 Create EventRepository
  - New file: `src/main/java/com/example/cryoguard/monitoring/infrastructure/persistence/EventRepository.java`
  - Methods: `findByContainerIdOrderByTimestampDesc()`, `findByContainerIdAndTypeOrderByTimestampDesc()`, `findByContainerIdAndSeverityOrderByTimestampDesc()`, `findByContainerIdAndTypeAndSeverityOrderByTimestampDesc()` with pagination

- [x] 1.6 Create GPS embeddable (if not exists)
  - New file: `src/main/java/com/example/cryoguard/monitoring/domain/valueobjects/GpsCoordinates.java`
  - Fields: `latitude` (-90 to 90), `longitude` (-180 to 180)

### Application Services

- [x] 2.1 Review ContainerCommandService
  - File: `src/main/java/com/example/cryoguard/monitoring/application/ContainerCommandService.java`
  - Already exists

- [x] 2.2 Review ContainerQueryService
  - File: `src/main/java/com/example/cryoguard/monitoring/application/ContainerQueryService.java`
  - Already exists

- [x] 2.3 Review TelemetryQueryService
  - File: `src/main/java/com/example/cryoguard/monitoring/application/TelemetryQueryService.java`
  - Already has `getTelemetryByContainerId()` method

### Controllers

- [x] 3.1 Review/Update ContainersController (CRUD + telemetry)
  - File: `src/main/java/com/example/cryoguard/monitoring/presentation/ContainersController.java`
  - Already has: POST `/containers`, GET `/containers`, GET `/containers/{id}`, PUT `/containers/{id}`, DELETE `/containers/{id}`
  - Already has: POST `/{id}/telemetry`, GET `/{id}/telemetry`
  - Needs: Verify it returns lowercase status strings in ContainerResource

- [x] 3.2 Create EventsController for /containers/{id}/events
  - New file: `src/main/java/com/example/cryoguard/monitoring/presentation/EventsController.java`
  - Endpoint: GET `/api/v1/containers/{id}/events`
  - Query params: `type`, `severity`, `page`, `size`
  - Returns: Page<EventResource> ordered by timestamp descending
  - Auth required: Yes (all roles)

### DTOs and Assemblers

- [x] 4.1 Review ContainerResource for API responses
  - File: `src/main/java/com/example/cryoguard/monitoring/presentation/resources/ContainerResource.java`
  - Fields: `id` (Long), `containerId` (UUID string), `name`, `status` (lowercase: active|inactive|maintenance), `currentLocation` (GpsLocationDTO with lat/lng), `temperature`, `humidity`, `batteryLevel`, `lastUpdate`, `productType`, `deviceId`, `operatorId`
  - `currentLocation` is `{ lat: BigDecimal, lng: BigDecimal } | null`

- [x] 4.2 Review ContainerResourceAssembler
  - File: `src/main/java/com/example/cryoguard/monitoring/presentation/assemblers/ContainerResourceAssembler.java`
  - Verify it maps entity to lowercase status string

- [x] 4.3 Review TelemetryReadingResource
  - File: `src/main/java/com/example/cryoguard/monitoring/presentation/resources/TelemetryReadingResource.java`
  - Fields: `id`, `containerId`, `temperature`, `humidity`, `vibration`, `gps`, `doorOpen`, `timestamp`
  - Note: TelemetryReading entity has `batteryLevel` field; ensure it's in resource

- [x] 4.4 Review TelemetryReadingResourceAssembler
  - File: `src/main/java/com/example/cryoguard/monitoring/presentation/assemblers/TelemetryReadingResourceAssembler.java`

- [x] 4.5 Create EventResource
  - New file: `src/main/java/com/example/cryoguard/monitoring/presentation/resources/EventResource.java`
  - Fields: `id`, `containerId`, `type` (lowercase), `severity` (lowercase), `message`, `timestamp`, `acknowledged`, `acknowledgedBy`, `acknowledgedAt`

- [x] 4.6 Create EventResourceAssembler
  - New file: `src/main/java/com/example/cryoguard/monitoring/presentation/assemblers/EventResourceAssembler.java`
  - Maps Event entity → EventResource with lowercase strings

### Telemetry Entity Updates

- [x] 5.1 Verify TelemetryReading entity has battery level
  - File: `src/main/java/com/example/cryoguard/monitoring/domain/entities/TelemetryReading.java`
  - Has: `id`, `containerId`, `timestamp`, `temperature`, `humidity`, `vibration`, `doorOpen`, `latitude`, `longitude`, `batteryLevel`
  - Need: Add `batteryLevel` field (integer 0-100)

### Testing Verification Tasks

- [ ] 6.1 Verify IAM tests exist for:
  - [ ] Sign-in with valid credentials → returns JWT + user
  - [ ] Sign-in with invalid credentials → returns 401
  - [ ] Sign-in with locked account → returns 403
  - [ ] GET /users with pagination
  - [ ] GET /users with role/status filters
  - [ ] POST /users creates user with lowercase role
  - [ ] PUT /users/{id} updates role/status
  - [ ] DELETE /users/{id} soft-deletes (sets deletedAt)

- [ ] 6.2 Verify Monitoring tests exist for:
  - [ ] GET /containers returns list with lowercase status
  - [ ] GET /containers?status=active filters correctly
  - [ ] GET /containers/{id}/telemetry returns latest reading
  - [ ] GET /containers/{id}/telemetry?from=&to= returns history
  - [ ] GET /containers/{id}/events returns paginated events
  - [ ] GET /containers/{id}/events?type=temperature filters correctly

---

## Notes

- Frontend expects lowercase strings for `role` and `status` in all API responses
- IAM: `role: 'admin'|'operator'|'supervisor'|'ngo'`, `status: 'active'|'inactive'|'locked'`
- Monitoring: `status: 'active'|'inactive'|'maintenance'`
- Events: `type: 'temperature'|'humidity'|'vibration'|'door'|'geofence'|'battery'|'offline'`, `severity: 'critical'|'warning'|'info'`
- JWT tokens are already implemented via `TokenServiceImpl`
- Soft delete is already implemented via `AuditableAbstractAggregateRoot` (uses `deletedAt`)
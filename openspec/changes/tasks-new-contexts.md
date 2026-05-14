# Implementation Tasks: 4 New Bounded Contexts

Implementation checklist for ACTUATORS, OPERATIONS, NOTIFICATIONS, and AUDIT bounded contexts.
Following DDD-layered architecture: domain → application → infrastructure → presentation.

---

## ACTUATORS Bounded Context

### Package Structure
- [ ] 1.1 Create `com.example.cryoguard.actuators` package structure
- [ ] 1.2 Create `actuators.domain.model.entities` package
- [ ] 1.3 Create `actuators.domain.model.valueobjects` package
- [ ] 1.4 Create `actuators.domain.commands` package
- [ ] 1.5 Create `actuators.domain.services` package
- [ ] 1.6 Create `actuators.application.internal.commandservices` package
- [ ] 1.7 Create `actuators.application.internal.queryservices` package
- [ ] 1.8 Create `actuators.infrastructure.persistence.jpa.repositories` package
- [ ] 1.9 Create `actuators.presentation.controllers` package

### Domain Layer
#### Value Objects
- [ ] 1.10 Create `ActuatorType` enum (LED, BUZZER, PELTIER, SERVO)
- [ ] 1.11 Create `ActuatorCommandEnum` enum (ON, OFF, TOGGLE, SET_COLOR)
- [ ] 1.12 Create `ActuatorState` value object (containerId, ledColor, ledBlinking, buzzerActive, peltierActive, servoLocked)

#### Entities
- [ ] 1.13 Create `ActuatorCommand` entity with fields: id (UUID), containerId, actuatorType, command, payload, issuedBy, timestamp

#### Domain Services
- [ ] 1.14 Create `ActuatorCommandService` domain service interface
- [ ] 1.15 Create `ActuatorQueryService` domain service interface

#### Commands
- [ ] 1.16 Create `SendActuatorCommandCommand` command class

### Application Layer
- [ ] 1.17 Create `ActuatorCommandServiceImpl` implementing ActuatorCommandService
- [ ] 1.18 Create `ActuatorQueryServiceImpl` implementing ActuatorQueryService

### Infrastructure Layer
- [ ] 1.19 Create `ActuatorCommandRepository` JPA repository
- [ ] 1.20 Create `ActuatorStateRepository` (in-memory map backed by command history)

### Presentation Layer
#### DTOs
- [ ] 1.21 Create `ActuatorStateDto` for GET /containers/{id}/actuators response
- [ ] 1.22 Create `ActuatorCommandDto` for command creation/request
- [ ] 1.23 Create `ActuatorCommandResponseDto` for command history items
- [ ] 1.24 Create `ActuatorHistoryPagedDto` for paginated history response

#### Assemblers
- [ ] 1.25 Create `ActuatorStateAssembler` (entity → DTO)
- [ ] 1.26 Create `ActuatorCommandAssembler` (entity ↔ DTO)

#### Controller
- [ ] 1.27 Create `ActuatorsController` with:
  - GET /containers/{id}/actuators
  - POST /containers/{id}/actuators/commands
  - GET /containers/{id}/actuators/history (paginated, filterable by actuatorType, issuedBy)

---

## OPERATIONS Bounded Context

### Package Structure
- [ ] 1.28 Create `com.example.cryoguard.operations` package structure
- [ ] 1.29 Create `operations.domain.model.entities` package
- [ ] 1.30 Create `operations.domain.model.valueobjects` package
- [ ] 1.31 Create `operations.domain.commands` package
- [ ] 1.32 Create `operations.domain.services` package
- [ ] 1.33 Create `operations.application.internal.commandservices` package
- [ ] 1.34 Create `operations.application.internal.queryservices` package
- [ ] 1.35 Create `operations.infrastructure.persistence.jpa.repositories` package
- [ ] 1.36 Create `operations.presentation.controllers` package

### Domain Layer
#### Value Objects
- [ ] 1.37 Create `OverrideStatus` enum (PENDING, APPROVED, REJECTED)
- [ ] 1.38 Create `OperationType` enum (USE, TRANSFER, DISCARD)
- [ ] 1.39 Create `OperationStatus` enum (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)

#### Entities
- [ ] 1.40 Create `OverrideRequest` entity with fields: id (UUID), containerId, requestedBy, approvedBy, status, reason, createdAt, resolvedAt
- [ ] 1.41 Create `Operation` entity with fields: id (UUID), containerId, type, status, requestedBy, completedBy, notes, createdAt, completedAt

#### Domain Services
- [ ] 1.42 Create `OverrideCommandService` domain service interface
- [ ] 1.43 Create `OperationCommandService` domain service interface
- [ ] 1.44 Create `OverrideQueryService` domain service interface
- [ ] 1.45 Create `OperationQueryService` domain service interface

#### Commands
- [ ] 1.46 Create `CreateOverrideRequestCommand` command class
- [ ] 1.47 Create `ApproveOverrideCommand` command class
- [ ] 1.48 Create `RejectOverrideCommand` command class
- [ ] 1.49 Create `CreateOperationCommand` command class
- [ ] 1.50 Create `CompleteOperationCommand` command class

### Application Layer
- [ ] 1.51 Create `OverrideCommandServiceImpl` implementing OverrideCommandService
- [ ] 1.52 Create `OperationCommandServiceImpl` implementing OperationCommandService
- [ ] 1.53 Create `OverrideQueryServiceImpl` implementing OverrideQueryService
- [ ] 1.54 Create `OperationQueryServiceImpl` implementing OperationQueryService

### Infrastructure Layer
- [ ] 1.55 Create `OverrideRequestRepository` JPA repository
- [ ] 1.56 Create `OperationRepository` JPA repository

### Presentation Layer
#### DTOs
- [ ] 1.57 Create `OverrideRequestDto` for override request responses
- [ ] 1.58 Create `CreateOverrideRequestDto` for POST /override-requests
- [ ] 1.59 Create `OverrideRequestPagedDto` for paginated list response
- [ ] 1.60 Create `OperationDto` for operation responses
- [ ] 1.61 Create `CreateOperationDto` for POST /operations
- [ ] 1.62 Create `CompleteOperationDto` for PUT /operations/{id}/complete
- [ ] 1.63 Create `OperationPagedDto` for paginated list response

#### Assemblers
- [ ] 1.64 Create `OverrideRequestAssembler` (entity ↔ DTO)
- [ ] 1.65 Create `OperationAssembler` (entity ↔ DTO)

#### Controllers
- [ ] 1.66 Create `OverrideRequestsController` with:
  - GET /override-requests (paginated, filterable by status, containerId, requestedBy)
  - POST /override-requests
  - PUT /override-requests/{id}/approve
  - PUT /override-requests/{id}/reject
- [ ] 1.67 Create `OperationsController` with:
  - GET /operations (paginated, filterable by type, status, containerId)
  - POST /operations
  - GET /operations/{id}
  - PUT /operations/{id}/complete

---

## NOTIFICATIONS Bounded Context

### Package Structure
- [ ] 1.68 Create `com.example.cryoguard.notifications` package structure
- [ ] 1.69 Create `notifications.domain.model.entities` package
- [ ] 1.70 Create `notifications.domain.model.valueobjects` package
- [ ] 1.71 Create `notifications.domain.commands` package
- [ ] 1.72 Create `notifications.domain.services` package
- [ ] 1.73 Create `notifications.application.internal.commandservices` package
- [ ] 1.74 Create `notifications.application.internal.queryservices` package
- [ ] 1.75 Create `notifications.infrastructure.persistence.jpa.repositories` package
- [ ] 1.76 Create `notifications.infrastructure.channels.push` package
- [ ] 1.77 Create `notifications.infrastructure.channels.email` package
- [ ] 1.78 Create `notifications.infrastructure.channels.sms` package
- [ ] 1.79 Create `notifications.presentation.controllers` package

### Domain Layer
#### Value Objects
- [ ] 1.80 Create `NotificationType` enum (PUSH, EMAIL, SMS)

#### Entities
- [ ] 1.81 Create `Notification` entity with fields: id (UUID), userId, containerId, alertId, type, title, body, read, createdAt
- [ ] 1.82 Create `NotificationPreference` entity with fields: userId (PK), pushEnabled, emailEnabled, smsEnabled, fcmToken

#### Domain Services
- [ ] 1.83 Create `NotificationService` domain service interface

#### Commands
- [ ] 1.84 Create `SendNotificationCommand` command class
- [ ] 1.85 Create `UpdatePreferencesCommand` command class

### Application Layer
- [ ] 1.86 Create `NotificationServiceImpl` implementing NotificationService
- [ ] 1.87 Create `NotificationQueryServiceImpl` implementing NotificationQueryService

### Infrastructure Layer
- [ ] 1.88 Create `NotificationRepository` JPA repository
- [ ] 1.89 Create `NotificationPreferenceRepository` JPA repository
- [ ] 1.90 Create `FcmPushChannel` for FCM push delivery
- [ ] 1.91 Create `SmtpEmailChannel` for SMTP email delivery
- [ ] 1.92 Create `SmsGatewayChannel` for SMS gateway delivery

### Presentation Layer
#### DTOs
- [ ] 1.93 Create `NotificationDto` for notification responses
- [ ] 1.94 Create `NotificationPagedDto` for paginated list response
- [ ] 1.95 Create `SendNotificationDto` for POST /notifications/send
- [ ] 1.96 Create `NotificationPreferenceDto` for preferences responses
- [ ] 1.97 Create `UpdatePreferencesDto` for PUT /notifications/preferences

#### Assemblers
- [ ] 1.98 Create `NotificationAssembler` (entity ↔ DTO)
- [ ] 1.99 Create `NotificationPreferenceAssembler` (entity ↔ DTO)

#### Controller
- [ ] 1.100 Create `NotificationsController` with:
  - GET /notifications (paginated, user's own notifications)
  - PUT /notifications/{id}/read
  - GET /notifications/preferences
  - PUT /notifications/preferences
  - POST /notifications/send (internal)

---

## AUDIT Bounded Context

### Package Structure
- [ ] 1.101 Create `com.example.cryoguard.audit` package structure
- [ ] 1.102 Create `audit.domain.model.entities` package
- [ ] 1.103 Create `audit.domain.model.valueobjects` package
- [ ] 1.104 Create `audit.domain.commands` package
- [ ] 1.105 Create `audit.domain.services` package
- [ ] 1.106 Create `audit.domain.events` package
- [ ] 1.107 Create `audit.application.internal.commandservices` package
- [ ] 1.108 Create `audit.application.internal.queryservices` package
- [ ] 1.109 Create `audit.infrastructure.persistence.jpa.repositories` package
- [ ] 1.110 Create `audit.presentation.controllers` package

### Domain Layer
#### Value Objects
- [ ] 1.111 Create `AuditResult` enum (SUCCESS, FAILURE) - lowercase in JSON

#### Entities
- [ ] 1.112 Create `AuditLog` entity with fields: id (UUID), userId, userName, action, module, result, details, ipAddress, userAgent, timestamp

#### Domain Services
- [ ] 1.113 Create `AuditService` domain service interface

#### Commands
- [ ] 1.114 Create `CreateAuditLogCommand` command class

#### Events
- [ ] 1.115 Create `AuditLogCreatedEvent` for internal event publication

### Application Layer
- [ ] 1.116 Create `AuditServiceImpl` implementing AuditService
- [ ] 1.117 Create `AuditQueryServiceImpl` implementing AuditQueryService

### Infrastructure Layer
- [ ] 1.118 Create `AuditLogRepository` JPA repository with custom query methods for filtering
- [ ] 1.119 Implement `AuditLogSpecification` for dynamic filter composition (module, userId, date range)

### Presentation Layer
#### DTOs
- [ ] 1.120 Create `AuditLogDto` for audit log responses
- [ ] 1.121 Create `AuditLogPagedDto` for paginated list response
- [ ] 1.122 Create `AuditStatsDto` for GET /audit-logs/stats response (totalLogs, successCount, failureCount, moduleCount)
- [ ] 1.123 Create `CreateAuditLogDto` for POST /audit-logs

#### Assemblers
- [ ] 1.124 Create `AuditLogAssembler` (entity ↔ DTO)

#### Controller
- [ ] 1.125 Create `AuditLogsController` with:
  - GET /audit-logs (paginated, filterable by module, userId, startDate, endDate)
  - GET /audit-logs/stats
  - POST /audit-logs (internal)

---

## Cross-Cutting Tasks

- [ ] 1.126 Configure Spring Security for new endpoints (JWT authentication patterns)
- [ ] 1.127 Add role-based access control:
  - ACTUATORS: authenticated users
  - OPERATIONS: OPERATOR, SUPERVISOR
  - NOTIFICATIONS: authenticated users (own notifications only)
  - AUDIT: ADMIN, SUPERVISOR (read), internal contexts (write)
- [ ] 1.128 Configure @Async for notification channel delivery
- [ ] 1.129 Add inter-context event listeners (OPERATIONS → ACTUATORS, NOTIFICATIONS, AUDIT)
- [ ] 1.130 Add application configuration for notification channels (FCM, SMTP, SMS)
- [ ] 1.131 Write unit tests for each bounded context
- [ ] 1.132 Write integration tests for key workflows

---

## Dependencies Between Contexts

| Order | Context | Depends On |
|-------|---------|------------|
| 1 | AUDIT | None (foundation) |
| 2 | OPERATIONS | AUDIT |
| 3 | ACTUATORS | AUDIT, OPERATIONS |
| 4 | NOTIFICATIONS | AUDIT |

**Note**: Tasks are numbered sequentially but can be implemented in dependency order.
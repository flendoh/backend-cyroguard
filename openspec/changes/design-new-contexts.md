# Design: 4 New Bounded Contexts

## Technical Approach

Design for four new bounded contexts (ACTUATORS, OPERATIONS, NOTIFICATIONS, AUDIT) following CryoGuard's existing DDD-layered architecture (domain → application → infrastructure → presentation). All contexts use JPA persistence, REST controllers, and domain events for cross-context communication via Spring ApplicationEvents.

---

## 1. ACTUATORS — Control y Actuación

### Package Structure

```
com.example.cryoguard.actuators
├── domain
│   ├── model
│   │   ├── entities/
│   │   │   └── ActuatorCommand.java
│   │   └── valueobjects/
│   │       ├── ActuatorState.java        # Value object (not persisted)
│   │       ├── ActuatorType.java         # LED | BUZZER | PELTIER | SERVO
│   │       └── ActuatorCommandEnum.java  # ON | OFF | TOGGLE | SET_COLOR
│   ├── commands/
│   │   └── SendActuatorCommandCommand.java
│   └── services/
│       ├── ActuatorCommandService.java
│       └── ActuatorQueryService.java
├── application
│   └── internal
│       ├── commandservices/
│       │   └── ActuatorCommandServiceImpl.java
│       └── queryservices/
│           └── ActuatorQueryServiceImpl.java
├── infrastructure
│   └── persistence
│       └── jpa/
│           └── repositories/
│               ├── ActuatorCommandRepository.java
│               └── ActuatorStateRepository.java   # in-memory or cache-backed
└── presentation
    └── controllers/
        └── ActuatorsController.java
```

### Entity Class Diagram

**ActuatorCommand.java** (persisted)
| Field | Type | Notes |
|-------|------|-------|
| id | String (UUID) | Primary key |
| containerId | String | NOT NULL, indexed |
| actuatorType | ActuatorType | LED, BUZZER, PELTIER, SERVO |
| command | ActuatorCommandEnum | ON, OFF, TOGGLE, SET_COLOR |
| payload | String | nullable (color value for SET_COLOR) |
| issuedBy | String | userId or 'SYSTEM' |
| timestamp | LocalDateTime | auto-set on creation |

**ActuatorState.java** (value object — not persisted)
| Field | Type |
|-------|------|
| containerId | String |
| ledColor | String (green/yellow/red/off) |
| ledBlinking | Boolean |
| buzzerActive | Boolean |
| peltierActive | Boolean |
| servoLocked | Boolean |

### API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /containers/{id}/actuators | Get current actuator state |
| POST | /containers/{id}/actuators/commands | Send actuator command |
| GET | /containers/{id}/actuators/history | Get command history (paginated) |

### Architecture Decisions

**Decision: ActuatorState as value object, not persisted**

**Choice**: Store current state in memory (map) backed by last command lookup
**Alternatives considered**: Persist state changes, use Redis
**Rationale**: State is derivable from command history. Avoids consistency issues between state table and command log.

**Decision: Enums uppercase in Java, lowercase in API**

**Choice**: ActuatorType.LED → "LED" in API
**Alternatives considered**: lowercase enum values
**Rationale**: Frontend mock uses uppercase (LED/BUZZER/PELTIER/SERVO). Consistent with AlertType pattern.

---

## 2. OPERATIONS — Operaciones / Flags

### Package Structure

```
com.example.cryoguard.operations
├── domain
│   ├── model
│   │   ├── entities/
│   │   │   ├── OverrideRequest.java
│   │   │   └── Operation.java
│   │   └── valueobjects/
│   │       ├── OverrideStatus.java     # pending | approved | rejected
│   │       ├── OperationType.java      # USE | TRANSFER | DISCARD
│   │       └── OperationStatus.java    # pending | in_progress | completed | cancelled
│   ├── commands/
│   │   ├── CreateOverrideRequestCommand.java
│   │   ├── ApproveOverrideCommand.java
│   │   ├── RejectOverrideCommand.java
│   │   ├── CreateOperationCommand.java
│   │   └── CompleteOperationCommand.java
│   └── services/
│       ├── OverrideCommandService.java
│       └── OperationCommandService.java
├── application
│   └── internal
│       ├── commandservices/
│       │   ├── OverrideCommandServiceImpl.java
│       │   └── OperationCommandServiceImpl.java
│       └── queryservices/
│           ├── OverrideQueryServiceImpl.java
│           └── OperationQueryServiceImpl.java
├── infrastructure
│   └── persistence
│       └── jpa/
│           └── repositories/
│               ├── OverrideRequestRepository.java
│               └── OperationRepository.java
└── presentation
    └── controllers/
        ├── OverrideRequestsController.java
        └── OperationsController.java
```

### Entity Class Diagram

**OverrideRequest.java** (persisted)
| Field | Type | Notes |
|-------|------|-------|
| id | String (UUID) | Primary key |
| containerId | String | NOT NULL, indexed |
| requestedBy | String | userId |
| approvedBy | String | nullable |
| status | OverrideStatus | pending/approved/rejected |
| reason | String | non-blank |
| createdAt | LocalDateTime | auto-set |
| resolvedAt | LocalDateTime | nullable, set on approve/reject |

**Operation.java** (persisted)
| Field | Type | Notes |
|-------|------|-------|
| id | String (UUID) | Primary key |
| containerId | String | NOT NULL, indexed |
| type | OperationType | USE, TRANSFER, DISCARD |
| status | OperationStatus | pending/in_progress/completed/cancelled |
| requestedBy | String | userId |
| completedBy | String | nullable |
| notes | String | nullable |
| createdAt | LocalDateTime | auto-set |
| completedAt | LocalDateTime | nullable, set on complete |

### API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /override-requests | List override requests (filterable) |
| POST | /override-requests | Create override request |
| PUT | /override-requests/{id}/approve | Approve override |
| PUT | /override-requests/{id}/reject | Reject override |
| GET | /operations | List operations (filterable) |
| POST | /operations | Create operation |
| GET | /operations/{id} | Get operation by ID |
| PUT | /operations/{id}/complete | Complete/cancel operation |

### Architecture Decisions

**Decision: Separate OverrideRequest and Operation entities**

**Choice**: Two distinct aggregates
**Alternatives considered**: Single entity with type discriminator
**Rationale**: Different lifecycles, different state machines, different approval workflows. Clear separation of concerns.

**Decision: Optimistic locking on status transitions**

**Choice**: Use @Version for concurrent approve/reject attempts
**Rationale**: Prevents race condition where two supervisors approve the same override simultaneously.

---

## 3. NOTIFICATIONS — Notificaciones

### Package Structure

```
com.example.cryoguard.notifications
├── domain
│   ├── model
│   │   ├── entities/
│   │   │   ├── Notification.java
│   │   │   └── NotificationPreference.java
│   │   └── valueobjects/
│   │       └── NotificationType.java     # PUSH | EMAIL | SMS
│   ├── commands/
│   │   ├── SendNotificationCommand.java
│   │   └── UpdatePreferencesCommand.java
│   └── services/
│       └── NotificationService.java
├── application
│   └── internal
│       ├── commandservices/
│       │   └── NotificationServiceImpl.java
│       └── queryservices/
│           └── NotificationQueryServiceImpl.java
├── infrastructure
│   ├── persistence
│   │   └── jpa/
│   │       └── repositories/
│   │           ├── NotificationRepository.java
│   │           └── NotificationPreferenceRepository.java
│   └── channels/                         # External integrations
│       ├── push/
│       │   └── FcmPushChannel.java
│       ├── email/
│       │   └── SmtpEmailChannel.java
│       └── sms/
│           └── SmsGatewayChannel.java
└── presentation
    └── controllers/
        └── NotificationsController.java
```

### Entity Class Diagram

**Notification.java** (persisted)
| Field | Type | Notes |
|-------|------|-------|
| id | String (UUID) | Primary key |
| userId | String | NOT NULL, indexed |
| containerId | String | nullable, indexed |
| alertId | String | nullable |
| type | NotificationType | PUSH, EMAIL, SMS |
| title | String | NOT NULL |
| body | String | NOT NULL |
| read | Boolean | default false |
| createdAt | LocalDateTime | auto-set |

**NotificationPreference.java** (persisted)
| Field | Type | Notes |
|-------|------|-------|
| userId | String | Primary key |
| pushEnabled | Boolean | default false |
| emailEnabled | Boolean | default false |
| smsEnabled | Boolean | default false |
| fcmToken | String | nullable |

### API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /notifications | List user's notifications (paginated) |
| PUT | /notifications/{id}/read | Mark notification as read |
| GET | /notifications/preferences | Get user preferences |
| PUT | /notifications/preferences | Update user preferences |
| POST | /notifications/send | Internal: trigger notification |

### Architecture Decisions

**Decision: Notification channel dispatch is async**

**Choice**: @Async for push/email/sms delivery
**Alternatives considered**: Synchronous delivery
**Rationale**: External services (FCM, SMTP, SMS gateway) have unpredictable latency. Non-blocking improves API response time. Failures logged but don't fail the notification creation.

**Decision: One NotificationPreference per user**

**Choice**: Single row with userId as PK
**Alternatives considered**: Multiple preference records per user
**Rationale**: Simple, enforced singleton per user. Upsert semantics with save().

---

## 4. AUDIT — Logs de Seguridad

### Package Structure

```
com.example.cryoguard.audit
├── domain
│   ├── model
│   │   ├── entities/
│   │   │   └── AuditLog.java
│   │   └── valueobjects/
│   │       └── AuditResult.java         # success | failure
│   ├── commands/
│   │   └── CreateAuditLogCommand.java
│   ├── services/
│   │   └── AuditService.java
│   └── events/
│       └── AuditLogCreatedEvent.java   # for internal publication
├── application
│   └── internal
│       ├── commandservices/
│       │   └── AuditServiceImpl.java
│       └── queryservices/
│           └── AuditQueryServiceImpl.java
├── infrastructure
│   └── persistence
│       └── jpa/
│           └── repositories/
│               └── AuditLogRepository.java   # with custom query methods
└── presentation
    └── controllers/
        └── AuditLogsController.java
```

### Entity Class Diagram

**AuditLog.java** (persisted — immutable)
| Field | Type | Notes |
|-------|------|-------|
| id | String (UUID) | Primary key |
| userId | String | actor userId, indexed |
| userName | String | actor display name |
| action | String | e.g., USER_LOGIN, CONTAINER_UPDATE |
| module | String | IAM, MONITORING, EVALUATION, etc. |
| result | AuditResult | success, failure |
| details | String | human-readable description |
| ipAddress | String | nullable, from request context |
| userAgent | String | nullable, from request context |
| timestamp | LocalDateTime | auto-set on creation |

### API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | /audit-logs | List audit logs (paginated, filterable) |
| GET | /audit-logs/stats | Aggregate statistics |
| POST | /audit-logs | Internal: create audit entry |

### Architecture Decisions

**Decision: Immutable audit log entries**

**Choice**: No update() or delete() methods
**Alternatives considered**: Soft delete, updateable records
**Rationale**: Audit trail integrity is paramount for compliance. Immutability ensures non-repudiation.

**Decision: Use Spring Data JPA Specification for dynamic filters**

**Choice**: Specification<AuditLog> pattern
**Alternatives considered**: Method name derived queries, JPQL with optional params
**Rationale**: Audit logs have many optional filters (module, userId, date range) that combine dynamically. Specification pattern scales better than 15 method name variations.

---

## Cross-Cutting Concerns

### Authentication / Authorization

All 4 contexts require JWT authentication (shared WebSecurityConfiguration). Role requirements:
- ACTUATORS: authenticated users
- OPERATIONS: OPERATOR, SUPERVISOR
- NOTIFICATIONS: authenticated users (own notifications only)
- AUDIT: ADMIN, SUPERVISOR (read), internal contexts (write)

### Inter-Context Communication

| From | To | Trigger |
|------|----|---------|
| OPERATIONS | ACTUATORS | Override approval → unlock servo |
| OPERATIONS | NOTIFICATIONS | Override request created → notify supervisors |
| OPERATIONS | AUDIT | Override approved/rejected |
| ACTUATORS | AUDIT | Commands issued |
| NOTIFICATIONS | AUDIT | Notification sent |

Communication via Spring ApplicationEvents and IamContextFacade (ACL pattern already established).

---

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `actuators/domain/model/entities/ActuatorCommand.java` | Create | Command entity |
| `actuators/domain/model/valueobjects/ActuatorState.java` | Create | State value object |
| `actuators/domain/model/valueobjects/ActuatorType.java` | Create | LED/BUZZER/PELTIER/SERVO enum |
| `actuators/domain/model/valueobjects/ActuatorCommandEnum.java` | Create | ON/OFF/TOGGLE/SET_COLOR enum |
| `actuators/infrastructure/persistence/jpa/repositories/ActuatorCommandRepository.java` | Create | JPA repository |
| `actuators/presentation/controllers/ActuatorsController.java` | Create | REST endpoints |
| `operations/domain/model/entities/OverrideRequest.java` | Create | Override request entity |
| `operations/domain/model/entities/Operation.java` | Create | Operation entity |
| `operations/domain/model/valueobjects/OverrideStatus.java` | Create | pending/approved/rejected enum |
| `operations/domain/model/valueobjects/OperationType.java` | Create | USE/TRANSFER/DISCARD enum |
| `operations/domain/model/valueobjects/OperationStatus.java` | Create | pending/in_progress/completed/cancelled enum |
| `operations/infrastructure/persistence/jpa/repositories/OverrideRequestRepository.java` | Create | JPA repository |
| `operations/infrastructure/persistence/jpa/repositories/OperationRepository.java` | Create | JPA repository |
| `operations/presentation/controllers/OverrideRequestsController.java` | Create | REST endpoints |
| `operations/presentation/controllers/OperationsController.java` | Create | REST endpoints |
| `notifications/domain/model/entities/Notification.java` | Create | Notification entity |
| `notifications/domain/model/entities/NotificationPreference.java` | Create | Preference entity |
| `notifications/domain/model/valueobjects/NotificationType.java` | Create | PUSH/EMAIL/SMS enum |
| `notifications/infrastructure/persistence/jpa/repositories/NotificationRepository.java` | Create | JPA repository |
| `notifications/infrastructure/persistence/jpa/repositories/NotificationPreferenceRepository.java` | Create | JPA repository |
| `notifications/infrastructure/channels/push/FcmPushChannel.java` | Create | FCM integration |
| `notifications/infrastructure/channels/email/SmtpEmailChannel.java` | Create | SMTP integration |
| `notifications/infrastructure/channels/sms/SmsGatewayChannel.java` | Create | SMS gateway integration |
| `notifications/presentation/controllers/NotificationsController.java` | Create | REST endpoints |
| `audit/domain/model/entities/AuditLog.java` | Create | Audit log entity |
| `audit/domain/model/valueobjects/AuditResult.java` | Create | success/failure enum |
| `audit/infrastructure/persistence/jpa/repositories/AuditLogRepository.java` | Create | JPA repository with Specifications |
| `audit/presentation/controllers/AuditLogsController.java` | Create | REST endpoints |

---

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Command services, state transitions | Mock repositories, verify entity state changes |
| Unit | Value objects (enums) | Verify lowercase API output |
| Integration | POST /containers/{id}/actuators/commands | Test command creation, history pagination |
| Integration | Override approve/reject state machine | Test status transitions, conflict detection |
| Integration | Notification preference upsert | Test create, update, defaults |
| Integration | Audit log filtering (module, userId, date range) | Test Specification composition |
| E2E | Full override flow: request → approve → servo unlock | Integration across OPERATIONS + ACTUATORS |
| E2E | Alert → Notification → Audit trail | Integration across EVALUATION → NOTIFICATIONS → AUDIT |

---

## Open Questions

- [ ] Should NOTIFICATIONS use a message queue (Kafka/RabbitMQ) for async delivery, or is @Async sufficient?
- [ ] Should ACTUATOR state be cached in Redis for sub-millisecond reads?
- [ ] Do we need audit log retention/archival policy (GDPR compliance)?
- [ ] Should OPERATIONS support batch complete for multiple operations at once?
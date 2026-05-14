# Design: Evaluation Bounded Context

## Context

The Evaluation bounded context handles alert management, incident tracking, and monitoring threshold configuration. It provides the alerting and rule configuration layer that the Alerts page and Settings page depend on.

## Package Structure

```
com.example.cryoguard.evaluation
├── domain
│   ├── entities
│   │   ├── Alert.java              # Main alert entity
│   │   └── MonitoringRule.java     # Threshold configuration
│   ├── repositories
│   │   ├── AlertRepository.java    # Alert persistence interface
│   │   └── MonitoringRuleRepository.java
│   ├── valueobjects
│   │   ├── AlertSeverity.java      # Enum: CRITICAL, WARNING, INFO
│   │   ├── AlertStatus.java        # Enum: OPEN, ACKNOWLEDGED, RESOLVED
│   │   └── AlertType.java          # Enum: TEMPERATURE, HUMIDITY, VIBRATION, DOOR, GEOFENCE
│   ├── commands                    # Write operations
│   └── queries                      # Read operations
├── application
│   ├── services
│   │   ├── AlertCommandService.java
│   │   ├── AlertQueryService.java
│   │   ├── MonitoringRuleCommandService.java
│   │   └── MonitoringRuleQueryService.java
│   └── impl
│       ├── AlertCommandServiceImpl.java
│       ├── AlertQueryServiceImpl.java
│       ├── MonitoringRuleCommandServiceImpl.java
│       └── MonitoringRuleQueryServiceImpl.java
├── infrastructure
│   └── persistence
│       ├── AlertRepository.java
│       └── MonitoringRuleRepository.java
└── presentation
    ├── controllers
    │   ├── AlertsController.java
    │   └── MonitoringRulesController.java
    ├── resources                    # DTOs for API
    ├── assemblers                   # Entity <-> Resource mappers
    └── dtos
```

## Entity Design

### Alert Entity

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | PK, auto-gen | Internal ID |
| alertId | String | UUID, unique | External identifier |
| containerId | Long | nullable | Reference to Container |
| type | AlertType | Enum | temperature, humidity, vibration, door, geofence |
| severity | AlertSeverity | Enum | critical, warning, info |
| message | String | not null | Human-readable description |
| timestamp | LocalDateTime | not null | When alert was generated |
| acknowledged | Boolean | default false | Acknowledgment flag |
| acknowledgedBy | Long | nullable | User who acknowledged |
| acknowledgedAt | LocalDateTime | nullable | When acknowledged |
| resolved | Boolean | default false | Resolution flag |
| resolvedBy | Long | nullable | User who resolved |
| resolvedAt | LocalDateTime | nullable | When resolved |

**Key JPA Annotations**:
- `@Entity @Table(name = "alerts")`
- `@Enumerated(EnumType.STRING)` for enums (stored as lowercase strings)
- Value objects for `AlertSeverity`, `AlertType`, `AlertStatus`

### MonitoringRule Value Object

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | PK, auto-gen | Internal ID |
| containerId | Long | nullable | Per-container rules |
| temperatureMin | BigDecimal | nullable | °C threshold |
| temperatureMax | BigDecimal | nullable | °C threshold |
| temperatureWarningOffset | BigDecimal | nullable | Warning offset |
| humidityMin | BigDecimal | nullable | % threshold |
| humidityMax | BigDecimal | nullable | % threshold |
| humidityWarningOffset | BigDecimal | nullable | Warning offset |
| maxVibration | BigDecimal | nullable | g threshold |
| criticalVibration | BigDecimal | nullable | Critical vibration |
| maxDoorOpenMinutes | Integer | nullable | Door duration |
| active | Boolean | default true | Rule active state |

## API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|-------------|----------|
| GET | /alerts | List alerts with filtering | Query: status, severity, containerId | List<AlertResource> |
| GET | /alerts/{id} | Get alert by ID | - | AlertResource |
| PUT | /alerts/{id}/acknowledge | Acknowledge alert | userId param | AlertResource |
| PUT | /alerts/{id}/resolve | Resolve alert | userId param | AlertResource |
| PUT | /alerts/{id}/escalate | Escalate alert | severity param | AlertResource |
| GET | /monitoring-rules | List all rules | Query: containerId, active | List<MonitoringRuleResource> |
| GET | /monitoring-rules/{id} | Get rule by ID | - | MonitoringRuleResource |
| PUT | /monitoring-rules | Bulk update rules | List<RuleUpdateRequest> | List<MonitoringRuleResource> |

## Architecture Decisions

### Decision: Store severity/type as Enum with String representation

**Choice**: Use JPA `@Enumerated(EnumType.STRING)` with Java enums
**Alternatives**: Store as VARCHAR, use @Convert with AttributeConverter
**Rationale**: Type safety at compile time, IDE auto-complete, and existing pattern in codebase

### Decision: Separate acknowledged/resolved flags over single status enum

**Choice**: Boolean flags for acknowledged and resolved
**Alternatives**: Single AlertStatus enum (OPEN, ACKNOWLEDGED, RESOLVED)
**Rationale**: Simpler filtering queries (resolved=false for open), spec requires both flags separately

### Decision: Bulk update for monitoring rules

**Choice**: PUT /monitoring-rules accepts array of rule updates
**Alternatives**: Individual PUT per rule parameter
**Rationale**: Frontend sends all threshold updates as a batch; reduces round-trips

## Service Layer Design

### AlertCommandService
```
acknowledgeAlert(alertId, userId) → Alert
  → Validate alert exists and not already acknowledged (409 if already)
  → Set acknowledged=true, acknowledgedBy, acknowledgedAt
  → Persist and return

resolveAlert(alertId, userId) → Alert
  → Validate alert exists and not already resolved (409 if already)
  → Set resolved=true, resolvedBy, resolvedAt
  → Set acknowledged=true if not already
  → Persist and return

escalateAlert(alertId, newSeverity) → Alert
  → Validate alert not resolved and not already critical
  → Validate escalation path (info→warning→critical only)
  → Update severity
  → Persist and return
```

### MonitoringRuleCommandService
```
updateRules(List<RuleUpdateRequest>) → List<MonitoringRule>
  → Validate each value within acceptable ranges
  → Update or insert rules for each parameter
  → Set updatedAt timestamp
```

## Key Design Decisions

| Area | Decision | Rationale |
|------|----------|-----------|
| Field naming | snake_case in JSON | Frontend expects lowercase with underscores |
| Enum storage | Lowercase string in DB | Matches frontend mock data |
| Acknowledgment | Must record who + when | Audit requirement EVAL-12 |
| Escalation | One level at a time only | info→warning→critical, not info→critical |
| Pagination | Default 20, page 0-indexed | Standard Spring Data pagination |
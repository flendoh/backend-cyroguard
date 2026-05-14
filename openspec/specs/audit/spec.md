# Audit Logs Specification

## Purpose

Provides a system-wide activity audit trail for security and compliance. Captures actions from all bounded contexts and supports querying with filters.

## Data Model

### AuditLog Entity
**IMPORTANT**: Field names and types MUST match frontend mock exactly.

```typescript
interface AuditLog {
  id: string;                           // UUID
  userId: string;                       // actor user ID
  userName: string;                     // actor display name
  action: string;                       // e.g., "USER_LOGIN", "CONTAINER_UPDATE"
  timestamp: string;                    // ISO timestamp
  details: string;                      // human-readable description
  // Additional fields for filtering:
  module?: string;                      // e.g., "IAM", "MONITORING" (for filtering)
  result?: 'success' | 'failure';       // for stats
}
```

**Note**: Frontend shows stats (Total Logs, Success, Failed, Modules) not a detailed table with all fields visible, but the full AuditLog should have all fields for filtering.

## API Endpoints

### GET /audit-logs

Returns a paginated list of audit logs with optional filtering.

**Frontend expects**: AuditLogs page with stats (Total Logs: 8, Success: 6, Failed: 1, Modules: 6)

#### Scenario: List audit logs with pagination
- GIVEN authenticated user
- WHEN client requests GET /audit-logs?page=1&limit=50
- THEN response SHALL contain array of AuditLog objects
- AND logs SHALL be ordered by timestamp descending
- AND pagination metadata SHALL be included (total, page, limit, pages)

#### Scenario: Filter by module
- GIVEN authenticated user
- WHEN client requests GET /audit-logs?module=IAM
- THEN response SHALL contain only logs where module equals "IAM"

#### Scenario: Filter by user
- GIVEN authenticated user
- WHEN client requests GET /audit-logs?userId=user-123
- THEN response SHALL contain only logs where userId equals "user-123"

#### Scenario: Filter by date range
- GIVEN authenticated user
- WHEN client requests GET /audit-logs?startDate=2026-01-01&endDate=2026-01-31
- THEN response SHALL contain only logs where timestamp falls within the range

#### Scenario: Combined filters
- GIVEN authenticated user
- WHEN client requests GET /audit-logs?module=MONITORING&userId=user-123&page=1&limit=25
- THEN response SHALL contain logs matching ALL specified filters

---

### GET /audit-logs/stats

Returns aggregate statistics for the dashboard.

#### Scenario: Retrieve stats
- GIVEN authenticated user
- WHEN client requests GET /audit-logs/stats
- THEN response SHALL contain:
  - totalLogs: number
  - successCount: number
  - failureCount: number
  - moduleCount: number (unique modules)

#### Scenario: Stats with date filter
- GIVEN authenticated user
- WHEN client requests GET /audit-logs/stats?startDate=2026-05-01&endDate=2026-05-14
- THEN stats SHALL be computed only for logs within the date range

---

### POST /audit-logs

Internal endpoint for other bounded contexts to record audit entries.

#### Scenario: IAM records user login
- GIVEN IAM context processes a successful login
- WHEN IAM calls POST /audit-logs with userId, userName, action, module, result
- THEN system SHALL create AuditLog with timestamp set to current time
- AND system SHALL return HTTP 201

#### Scenario: Evaluation records override approval
- GIVEN an override was approved for container
- WHEN evaluation context calls POST /audit-logs with action: "OVERRIDE_APPROVED", module: "EVALUATION"
- THEN system SHALL record the audit entry
- AND ipAddress and userAgent SHALL be captured from request context if available

## Module Taxonomy

| Module | Description |
|--------|-------------|
| IAM | Identity and access management (login, logout, user management) |
| MONITORING | Real-time monitoring and sensor data |
| EVALUATION | Alert evaluation and override processing |
| ACTUATORS | Actuator commands and responses |
| LOGISTICS | Container and route management |
| OPERATIONS | Operation records and override requests |
| NOTIFICATIONS | Notification delivery |

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| AUDIT-01 | Audit logs MUST be ordered by timestamp descending | MUST |
| AUDIT-02 | Audit logs MUST support pagination with configurable page size | MUST |
| AUDIT-03 | Audit logs MUST support filtering by module | MUST |
| AUDIT-04 | Audit logs MUST support filtering by userId | MUST |
| AUDIT-05 | Audit logs MUST support filtering by date range | MUST |
| AUDIT-06 | Audit stats MUST return totalLogs, successCount, failureCount, moduleCount | MUST |
| AUDIT-07 | Audit log creation MUST capture timestamp automatically | MUST |
| AUDIT-08 | Audit log creation MUST capture ipAddress and userAgent when available | SHOULD |
| AUDIT-09 | Audit logs SHOULD be immutable once created | MUST |
| AUDIT-10 | Audit log result MUST use lowercase: 'success', 'failure' | MUST |
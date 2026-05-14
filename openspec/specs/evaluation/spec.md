# Evaluation Specification

## Purpose

Evaluation handles alert management, incident tracking, and monitoring threshold configuration for CryoGuard. This bounded context provides the alerting and rule configuration layer that the Alerts page and Settings page depend on.

## Data Model

### Alert Entity
**IMPORTANT**: Field names and types MUST match frontend mock exactly.

```typescript
interface Alert {
  id: string;                           // UUID
  containerId: string;                  // reference to Container
  type: 'temperature' | 'humidity' | 'vibration' | 'door' | 'geofence';  // lowercase to match frontend
  severity: 'critical' | 'warning' | 'info';  // lowercase to match frontend
  message: string;                      // human-readable description
  timestamp: string;                   // ISO timestamp
  acknowledged: boolean;                // default false
  acknowledgedBy?: string;             // user id
  acknowledgedAt?: string;             // ISO timestamp
  resolved?: boolean;                   // default false (for UI filtering)
  resolvedBy?: string;
  resolvedAt?: string;
}
```

### MonitoringRule Value Object
```typescript
interface MonitoringRule {
  id: string;                          // UUID
  name: string;                        // e.g., "Temperature Range"
  parameter: 'temperature_min' | 'temperature_max' | 'humidity_min' | 'humidity_max' | 'vibration_threshold';  // lowercase, snake_case
  value: number;                       // threshold value
  unit: string;                        // e.g., "°C", "%", "g"
  updatedAt: string;                    // ISO timestamp
}
```

## API Endpoints

### GET /alerts

Returns a list of alerts with optional filtering.

**Frontend expects**: Alerts page with stats (Critical: 2, Warnings: 2, Open: 2, Resolved: 1)

#### Scenario: List all alerts
- GIVEN authenticated user
- WHEN client requests GET /alerts
- THEN response SHALL contain array of Alert objects
- AND each alert SHALL include all fields as defined in Alert interface
- AND alerts SHALL be ordered by timestamp descending (newest first)

#### Scenario: Filter alerts by severity
- GIVEN authenticated user
- WHEN client requests GET /alerts?severity=critical
- THEN response SHALL contain only alerts with severity 'critical'

#### Scenario: Filter alerts by status (open)
- GIVEN authenticated user
- WHEN client requests GET /alerts?status=open
- THEN response SHALL contain only alerts where resolved=false

#### Scenario: Filter alerts by status (resolved)
- GIVEN authenticated user
- WHEN client requests GET /alerts?status=resolved
- THEN response SHALL contain only alerts where resolved=true

#### Scenario: List alerts with pagination
- GIVEN authenticated user
- WHEN client requests GET /alerts?page=0&size=20
- THEN response SHALL include pagination metadata (totalElements, totalPages)
- AND only 20 alerts SHALL be returned per page

---

### GET /alerts/{id}

Returns a single alert by ID.

#### Scenario: Get alert by valid ID
- GIVEN an alert with id 123 exists
- WHEN client requests GET /alerts/123
- THEN response SHALL contain the Alert object

#### Scenario: Get non-existent alert
- GIVEN no alert with id 999 exists
- WHEN client requests GET /alerts/999
- THEN the response SHALL return HTTP 404 Not Found

---

### PUT /alerts/{id}/acknowledge

Marks an alert as acknowledged (read).

#### Scenario: Acknowledge open alert
- GIVEN an alert with id 123 exists with acknowledged=false
- WHEN client submits PUT /alerts/123/acknowledge
- THEN alert acknowledged SHALL be set to true
- AND acknowledgedBy SHALL be set to authenticated user id
- AND acknowledgedAt SHALL be set to current timestamp
- AND resolved SHALL remain false
- AND the response SHALL include the updated alert

#### Scenario: Acknowledge already acknowledged alert
- GIVEN an alert with id 123 exists with acknowledged=true
- WHEN client submits PUT /alerts/123/acknowledge
- THEN the response SHALL return HTTP 409 Conflict

---

### PUT /alerts/{id}/resolve

Closes and resolves an alert.

#### Scenario: Resolve open alert
- GIVEN an alert with id 123 exists with resolved=false
- WHEN client submits PUT /alerts/123/resolve
- THEN alert resolved SHALL be set to true
- AND resolvedBy SHALL be set to authenticated user id
- AND resolvedAt SHALL be set to current timestamp
- AND acknowledged SHALL be set to true if not already
- AND the response SHALL include the updated alert

#### Scenario: Resolve already resolved alert
- GIVEN an alert with id 123 exists with resolved=true
- WHEN client submits PUT /alerts/123/resolve
- THEN the response SHALL return HTTP 409 Conflict

---

### PUT /alerts/{id}/escalate

Escalates an alert to higher priority.

#### Scenario: Escalate warning alert to critical
- GIVEN an alert with id 123 exists with severity 'warning'
- WHEN client submits PUT /alerts/123/escalate
- THEN alert severity SHALL be updated to 'critical'

#### Scenario: Escalate info alert to warning
- GIVEN an alert with id 123 exists with severity 'info'
- WHEN client submits PUT /alerts/123/escalate
- THEN alert severity SHALL be updated to 'warning'

#### Scenario: Escalate already critical alert
- GIVEN an alert with id 123 exists with severity 'critical'
- WHEN client submits PUT /alerts/123/escalate
- THEN the response SHALL return HTTP 409 Conflict

#### Scenario: Escalate resolved alert
- GIVEN an alert with id 123 exists with resolved=true
- WHEN client submits PUT /alerts/123/escalate
- THEN the response SHALL return HTTP 409 Conflict

---

### GET /monitoring-rules

Returns all monitoring threshold rules.

**Frontend expects**: Settings page with Temperature range (min/max), Humidity range (min/max), Vibration threshold

#### Scenario: List all monitoring rules
- GIVEN authenticated user
- WHEN client requests GET /monitoring-rules
- THEN response SHALL contain array of MonitoringRule objects
- AND each rule SHALL include id, name, parameter, value, unit, updatedAt

#### Scenario: Rules reflect current configuration
- GIVEN monitoring rules configured with temperature_min=-2, temperature_max=8
- WHEN client requests GET /monitoring-rules
- THEN response SHALL include rule with parameter 'temperature_min' and value -2
- AND response SHALL include rule with parameter 'temperature_max' and value 8

---

### PUT /monitoring-rules

Updates monitoring threshold rules.

#### Scenario: Update temperature thresholds
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /monitoring-rules with [{ "parameter": "temperature_min", "value": -5 }, { "parameter": "temperature_max", "value": 10 }]
- THEN temperature_min rule value SHALL be updated to -5
- AND temperature_max rule value SHALL be updated to 10
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update humidity thresholds
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /monitoring-rules with [{ "parameter": "humidity_min", "value": 30 }, { "parameter": "humidity_max", "value": 70 }]
- THEN humidity_min rule value SHALL be updated to 30
- AND humidity_max rule value SHALL be updated to 70

#### Scenario: Update vibration threshold
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /monitoring-rules with [{ "parameter": "vibration_threshold", "value": 2.5 }]
- THEN vibration_threshold rule value SHALL be updated to 2.5

#### Scenario: Update with invalid value
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /monitoring-rules with [{ "parameter": "temperature_max", "value": 1000 }]
- THEN the response SHALL return HTTP 400 Bad Request

#### Scenario: Update without permission
- GIVEN authenticated user has NGO role
- WHEN client submits PUT /monitoring-rules with updated values
- THEN the response SHALL return HTTP 403 Forbidden

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| EVAL-01 | Alert severity MUST use lowercase: 'critical', 'warning', 'info' | MUST |
| EVAL-02 | Alert type MUST use lowercase: 'temperature', 'humidity', 'vibration', 'door', 'geofence' | MUST |
| EVAL-03 | Alert list MUST support filtering by severity | MUST |
| EVAL-04 | Alert list MUST support filtering by status (open/resolved) | MUST |
| EVAL-05 | Alert list MUST be ordered by timestamp descending | MUST |
| EVAL-06 | Alert list MUST support pagination | MUST |
| EVAL-07 | Alerts MUST be acknowledged before they can be resolved | SHOULD |
| EVAL-08 | Escalation MUST only increase severity (info→warning→critical) | MUST |
| EVAL-09 | Escalation MUST NOT be allowed on already critical or resolved alerts | MUST |
| EVAL-10 | Monitoring rules MUST use snake_case parameters: temperature_min, temperature_max, humidity_min, humidity_max, vibration_threshold | MUST |
| EVAL-11 | Monitoring rule updates MUST validate value ranges per parameter type | MUST |
| EVAL-12 | Alert acknowledgment MUST record who acknowledged and when | MUST |
| EVAL-13 | Alert resolution MUST record who resolved and when | MUST |
# Actuators Specification

## Purpose

Actuators manages physical device control (LED, Buzzer, Peltier, Servo) and command dispatch for container hardware. This bounded context receives commands from the Evaluation context and exposes manual override endpoints for operators and supervisors.

## Data Model

### ActuatorState Value Object

- `containerId`: string (reference to Container)
- `ledColor`: 'green' | 'yellow' | 'red' | 'off' (lowercase)
- `ledBlinking`: boolean
- `buzzerActive`: boolean
- `peltierActive`: boolean
- `servoLocked`: boolean

### ActuatorCommand Entity

- `id`: string (UUID)
- `containerId`: string (reference to Container)
- `actuatorType`: 'LED' | 'BUZZER' | 'PELTIER' | 'SERVO' (uppercase)
- `command`: 'ON' | 'OFF' | 'TOGGLE' | 'SET_COLOR' (uppercase)
- `payload`: string | null (e.g., color value for LED)
- `issuedBy`: string (userId or 'SYSTEM')
- `timestamp`: string (ISO timestamp)

## API Endpoints

### GET /containers/{id}/actuators

Returns current actuator state for a container.

#### Scenario: Get current state
- GIVEN a container with id 123 exists
- WHEN client requests GET /containers/123/actuators
- THEN response SHALL contain ledColor, ledBlinking, buzzerActive, peltierActive, servoLocked
- AND lastUpdated SHALL reflect the most recent state change

#### Scenario: Get state for non-existent container
- GIVEN no container with id 999 exists
- WHEN client requests GET /containers/999/actuators
- THEN response SHALL return HTTP 404 Not Found

---

### POST /containers/{id}/actuators/commands

Sends a command to a specific actuator.

#### Scenario: Turn on buzzer manually
- GIVEN a container with id 123 exists and buzzer is off
- WHEN client submits POST /containers/123/actuators/commands with actuatorType 'BUZZER', command 'ON', issuedBy 'user-456'
- THEN buzzerActive SHALL be set to true
- AND command SHALL be recorded in history
- AND response SHALL include the created command with HTTP 201

#### Scenario: Set LED to red color
- GIVEN a container with id 123 exists
- WHEN client submits POST /containers/123/actuators/commands with actuatorType 'LED', command 'SET_COLOR', payload 'red', issuedBy 'system'
- THEN ledColor SHALL be set to 'red'
- AND ledBlinking SHALL default to false unless system rule specifies otherwise

#### Scenario: Unlock servo via override
- GIVEN a container with id 123 exists and servo is locked
- WHEN client submits POST /containers/123/actuators/commands with actuatorType 'SERVO', command 'OFF', issuedBy 'supervisor-789'
- THEN servoLocked SHALL be set to false
- AND command SHALL be recorded with issuedBy for audit

#### Scenario: Command to non-existent container
- GIVEN no container with id 999 exists
- WHEN client submits POST /containers/999/actuators/commands
- THEN response SHALL return HTTP 404 Not Found

#### Scenario: Invalid actuator type
- GIVEN a container with id 123 exists
- WHEN client submits POST /containers/123/actuators/commands with actuatorType 'INVALID'
- THEN response SHALL return HTTP 400 Bad Request

---

### GET /containers/{id}/actuators/history

Returns command execution history for audit purposes.

#### Scenario: Retrieve command history
- GIVEN a container with id 123 has multiple commands in history
- WHEN client requests GET /containers/123/actuators/history
- THEN response SHALL contain array of commands ordered by issuedAt descending
- AND each command SHALL include id, actuatorType, command, payload, issuedBy, issuedAt

#### Scenario: Filter history by actuator type
- GIVEN a container with id 123 has LED and BUZZER commands
- WHEN client requests GET /containers/123/actuators/history?actuatorType=LED
- THEN response SHALL contain only commands where actuatorType is 'LED'

#### Scenario: Filter history by issuer
- GIVEN a container with id 123 has commands from multiple users and system
- WHEN client requests GET /containers/123/actuators/history?issuedBy=user-456
- THEN response SHALL contain only commands issued by 'user-456'

#### Scenario: Paginate history
- GIVEN a container with id 123 has more than 50 commands
- WHEN client requests GET /containers/123/actuators/history?page=0&size=25
- THEN response SHALL include pagination metadata (totalElements, totalPages)
- AND only 25 commands SHALL be returned per page

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| ACT-01 | GET /containers/{id}/actuators MUST return current state of all actuators | MUST |
| ACT-02 | POST /containers/{id}/actuators/commands MUST record every command for audit | MUST |
| ACT-03 | Command history MUST support filtering by actuatorType and issuedBy | SHOULD |
| ACT-04 | Command history MUST support pagination | SHOULD |
| ACT-05 | Invalid actuator types MUST be rejected with HTTP 400 | MUST |
| ACT-06 | Commands issued by 'SYSTEM' MUST be distinguishable from manual overrides | MUST |
| ACT-07 | Servo unlock commands MUST be logged with issuer identity | MUST |
| ACT-08 | LED SET_COLOR commands MUST accept valid color values: green, yellow, red, off | MUST |
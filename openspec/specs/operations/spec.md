# Operations Specification

## Purpose

Operations handles container use requests, product transfer documentation, and override approval workflow. This bounded context supports US18 (physical override) and US19 (remote override) requirements for authorization and audit trails.

## Data Model

### OverrideRequest Entity

- `id`: string (UUID)
- `containerId`: string (reference to Container)
- `requestedBy`: string (userId of requester)
- `approvedBy`: string | null (userId of approver)
- `status`: enum ('pending', 'approved', 'rejected')
- `reason`: string (justification for override)
- `createdAt`: timestamp
- `resolvedAt`: timestamp | null

### Operation Entity

- `id`: string (UUID)
- `containerId`: string (reference to Container)
- `type`: enum ('USE', 'TRANSFER', 'DISCARD')
- `status`: enum ('pending', 'in_progress', 'completed', 'cancelled')
- `requestedBy`: string (userId)
- `completedBy`: string | null (userId)
- `notes`: string | null
- `createdAt`: timestamp
- `completedAt`: timestamp | null

## API Endpoints

### GET /override-requests

Returns all override requests with optional filtering.

#### Scenario: List all pending requests
- GIVEN multiple override requests exist
- WHEN client requests GET /override-requests?status=pending
- THEN response SHALL contain only requests where status is 'pending'
- AND ordered by createdAt descending

#### Scenario: Filter by container
- GIVEN override requests exist for multiple containers
- WHEN client requests GET /override-requests?containerId=123
- THEN response SHALL contain only requests for container 123

#### Scenario: Filter by requester
- GIVEN override requests exist from multiple users
- WHEN client requests GET /override-requests?requestedBy=user-456
- THEN response SHALL contain only requests requested by 'user-456'

---

### POST /override-requests

Creates a new override request.

#### Scenario: Operator requests override
- GIVEN a container with id 123 exists and is currently locked
- WHEN operator submits POST /override-requests with containerId '123', requestedBy 'user-456', reason 'Emergency access needed for temperature check'
- THEN override request SHALL be created with status 'pending'
- AND createdAt SHALL be set to current timestamp
- AND response SHALL include HTTP 201 with the created request

#### Scenario: Request override for non-existent container
- GIVEN no container with id 999 exists
- WHEN client submits POST /override-requests with containerId '999'
- THEN response SHALL return HTTP 404 Not Found

#### Scenario: Request override without reason
- GIVEN a container with id 123 exists
- WHEN client submits POST /override-requests with reason ''
- THEN response SHALL return HTTP 400 Bad Request

---

### PUT /override-requests/{id}/approve

Approves a pending override request.

#### Scenario: Supervisor approves override
- GIVEN an override request with id abc-123 exists with status 'pending'
- WHEN supervisor submits PUT /override-requests/abc-123/approve with approvedBy 'supervisor-789'
- THEN status SHALL be updated to 'approved'
- AND approvedBy SHALL be set to 'supervisor-789'
- AND resolvedAt SHALL be set to current timestamp
- AND response SHALL return HTTP 200

#### Scenario: Approve already resolved request
- GIVEN an override request with id abc-123 exists with status 'approved'
- WHEN client submits PUT /override-requests/abc-123/approve
- THEN response SHALL return HTTP 409 Conflict

---

### PUT /override-requests/{id}/reject

Rejects a pending override request.

#### Scenario: Supervisor rejects override
- GIVEN an override request with id abc-123 exists with status 'pending'
- WHEN supervisor submits PUT /override-requests/abc-123/reject
- THEN status SHALL be updated to 'rejected'
- AND resolvedAt SHALL be set to current timestamp
- AND response SHALL return HTTP 200

#### Scenario: Reject non-existent request
- GIVEN no override request with id xyz-999 exists
- WHEN client submits PUT /override-requests/xyz-999/reject
- THEN response SHALL return HTTP 404 Not Found

---

### GET /operations

Returns all operations with optional filtering.

#### Scenario: List all operations
- GIVEN multiple operations exist
- WHEN client requests GET /operations
- THEN response SHALL contain array of operations ordered by createdAt descending

#### Scenario: Filter operations by container
- GIVEN operations exist for multiple containers
- WHEN client requests GET /operations?containerId=123
- THEN response SHALL contain only operations for container 123

#### Scenario: Filter operations by type
- GIVEN operations exist of types USE, TRANSFER, DISCARD
- WHEN client requests GET /operations?type=TRANSFER
- THEN response SHALL contain only operations where type is 'TRANSFER'

#### Scenario: Filter operations by status
- GIVEN operations exist with status pending and completed
- WHEN client requests GET /operations?status=completed
- THEN response SHALL contain only operations where status is 'completed'

#### Scenario: Paginate operations
- GIVEN more than 50 operations exist
- WHEN client requests GET /operations?page=0&size=25
- THEN response SHALL include pagination metadata
- AND only 25 operations SHALL be returned per page

---

### POST /operations

Creates a new operation record.

#### Scenario: Record product use
- GIVEN a container with id 123 exists
- WHEN client submits POST /operations with containerId '123', type 'USE', requestedBy 'user-456', notes 'Biopsy sample removed for analysis'
- THEN operation SHALL be created with status 'pending'
- AND createdAt SHALL be set to current timestamp
- AND response SHALL include HTTP 201 with the created operation

#### Scenario: Record product transfer
- GIVEN a container with id 123 exists
- WHEN client submits POST /operations with containerId '123', type 'TRANSFER', requestedBy 'user-456', notes 'Transfer to Region Hospital'
- THEN operation SHALL be created with status 'pending'

#### Scenario: Record product discard
- GIVEN a container with id 123 exists with expired contents
- WHEN client submits POST /operations with containerId '123', type 'DISCARD', requestedBy 'admin-001', reason 'Product expired'
- THEN operation SHALL be created with status 'pending'

---

### GET /operations/{id}

Returns a specific operation by ID.

#### Scenario: Get existing operation
- GIVEN an operation with id op-789 exists
- WHEN client requests GET /operations/op-789
- THEN response SHALL contain id, containerId, type, status, requestedBy, notes, createdAt

#### Scenario: Get non-existent operation
- GIVEN no operation with id op-999 exists
- WHEN client requests GET /operations/op-999
- THEN response SHALL return HTTP 404 Not Found

---

### PUT /operations/{id}/complete

Marks an operation as completed.

#### Scenario: Complete pending operation
- GIVEN an operation with id op-789 exists with status 'pending'
- WHEN client submits PUT /operations/op-789/complete with completedBy 'user-456'
- THEN status SHALL be updated to 'completed'
- AND completedBy SHALL be set to 'user-456'
- AND completedAt SHALL be set to current timestamp
- AND response SHALL return HTTP 200

#### Scenario: Complete already completed operation
- GIVEN an operation with id op-789 exists with status 'completed'
- WHEN client submits PUT /operations/op-789/complete
- THEN response SHALL return HTTP 409 Conflict

#### Scenario: Cancel operation
- GIVEN an operation with id op-789 exists with status 'pending'
- WHEN client submits PUT /operations/op-789/complete with status 'cancelled'
- THEN status SHALL be updated to 'cancelled'
- AND completedAt SHALL be set to current timestamp

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| OPS-01 | Override requests MUST be created with status 'pending' | MUST |
| OPS-02 | Override requests MUST support approve and reject transitions | MUST |
| OPS-03 | Approved override requests MUST record who approved and when | MUST |
| OPS-04 | Operations MUST support types USE, TRANSFER, DISCARD | MUST |
| OPS-05 | Operations MUST support status transitions: pending -> in_progress -> completed/cancelled | MUST |
| OPS-06 | Completed operations MUST record who completed and when | MUST |
| OPS-07 | Override request list MUST support filtering by containerId, status, requestedBy | MUST |
| OPS-08 | Operations list MUST support filtering by containerId, type, status | MUST |
| OPS-09 | All lists MUST support pagination | SHOULD |
| OPS-10 | Reason field MUST be non-empty when creating override requests | MUST |
| OPS-11 | Operations MUST support optional notes field | MAY |
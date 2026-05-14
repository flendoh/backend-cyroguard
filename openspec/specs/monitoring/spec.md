# Monitoring Specification

## Purpose

Monitoring handles real-time telemetry data collection, container state management, and sensor event tracking for CryoGuard's cold chain logistics system.

## Data Model

### Container Entity
**IMPORTANT**: Field names and types MUST match frontend mock exactly.

```typescript
interface Container {
  id: string;                          // UUID
  name: string;                       // human-readable name
  status: 'active' | 'inactive' | 'maintenance';  // lowercase to match frontend
  currentLocation: { lat: number; lng: number } | null;
  temperature: number | null;         // from latest telemetry
  humidity: number | null;            // from latest telemetry
  batteryLevel: number;               // 0-100 percentage
  lastUpdate: string;                 // ISO timestamp
  productType: string;                // e.g., "vaccines", "insulin", "blood"
  deviceId?: string;                 // hardware identifier (optional for list)
  operatorId?: string;               // assigned operator (optional)
}
```

### Telemetry Value Object
```typescript
interface Telemetry {
  id: string;
  containerId: string;
  temperature: number | null;        // Celsius, -50 to 50
  humidity: number | null;           // percentage, 0-100
  vibration: number | null;          // g-force, 0-10
  gps: {
    lat: number;
    lng: number;
    accuracy?: number;               // meters
  } | null;
  doorOpen: boolean;
  timestamp: string;                 // ISO timestamp
}
```

### Event Entity
```typescript
interface Event {
  id: string;
  containerId: string;
  type: 'temperature' | 'humidity' | 'vibration' | 'door' | 'geofence' | 'battery' | 'offline';
  severity: 'critical' | 'warning' | 'info';
  message: string;
  timestamp: string;
  acknowledged: boolean;
  acknowledgedBy?: string;
  acknowledgedAt?: string;
}
```

## API Endpoints

### GET /containers

Returns a list of containers with optional status filtering.

**Frontend expects**: DashboardHome, Containers page

#### Scenario: List all containers
- GIVEN authenticated user
- WHEN client requests GET /containers
- THEN response SHALL contain array of Container objects
- AND each container SHALL include all fields as defined in Container interface
- AND temperature and humidity SHALL be populated from latest telemetry

#### Scenario: Filter containers by status
- GIVEN authenticated user
- WHEN client requests GET /containers?status=active
- THEN response SHALL contain only containers with status 'active'

#### Scenario: Filter containers by product type
- GIVEN authenticated user
- WHEN client requests GET /containers?productType=vaccines
- THEN response SHALL contain only containers with productType 'vaccines'

#### Scenario: List containers with pagination
- GIVEN authenticated user
- WHEN client requests GET /containers?page=0&size=10
- THEN response SHALL include pagination metadata (totalElements, totalPages)

---

### POST /containers

Creates a new container.

#### Scenario: Create container with valid data
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /containers with name "Vaccine Cold Box 1", productType "vaccines"
- THEN container SHALL be created with status 'active'
- AND createdAt and updatedAt SHALL be set to current timestamp
- AND batteryLevel SHALL default to 100
- AND currentLocation SHALL default to null
- AND temperature and humidity SHALL default to null
- AND the response SHALL include the created container

#### Scenario: Create container with missing required fields
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /containers with missing name
- THEN the response SHALL return HTTP 400 Bad Request
- AND error SHALL specify "name is required"

---

### GET /containers/{id}

Returns a single container by ID.

#### Scenario: Get container by ID
- GIVEN authenticated user
- WHEN client requests GET /containers/123
- THEN response SHALL contain full Container object

#### Scenario: Get non-existent container
- GIVEN no container with id 999 exists
- WHEN client requests GET /containers/999
- THEN the response SHALL return HTTP 404 Not Found

---

### PUT /containers/{id}

Updates an existing container.

#### Scenario: Update container status
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /containers/123 with status "maintenance"
- THEN container status SHALL be updated to 'maintenance'
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update container location
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits PUT /containers/123 with currentLocation { lat: -33.8688, lng: 151.2093 }
- THEN container currentLocation SHALL be updated
- AND updatedAt SHALL be set to current timestamp

---

### DELETE /containers/{id}

Removes a container from active use.

#### Scenario: Delete container
- GIVEN authenticated user has ADMIN role
- WHEN client requests DELETE /containers/123
- THEN container SHALL be marked as deleted (soft delete) or removed
- AND the response SHALL return HTTP 204 No Content

---

### GET /containers/{id}/telemetry

Returns telemetry data for a specific container.

#### Scenario: Get latest telemetry
- GIVEN a container with id 123 exists with telemetry data
- WHEN client requests GET /containers/123/telemetry
- THEN response SHALL contain Telemetry object with latest readings
- AND timestamp SHALL be the most recent available

#### Scenario: Get telemetry history
- GIVEN a container with id 123 has multiple telemetry records
- WHEN client requests GET /containers/123/telemetry?from=2026-05-01T00:00:00Z&to=2026-05-14T23:59:59Z
- THEN response SHALL contain an array of Telemetry records within the time range
- AND records SHALL be ordered by timestamp descending

#### Scenario: Get telemetry for container with no data
- GIVEN a container with id 123 exists but has no telemetry records
- WHEN client requests GET /containers/123/telemetry
- THEN response SHALL return HTTP 404 Not Found

---

### GET /containers/{id}/events

Returns an event feed for a specific container.

#### Scenario: Get recent events
- GIVEN a container with id 123 has events
- WHEN client requests GET /containers/123/events
- THEN response SHALL contain array of Event objects
- AND events SHALL be ordered by timestamp descending (newest first)
- AND default limit SHALL be 50 events

#### Scenario: Filter events by type
- GIVEN a container with id 123 has multiple event types
- WHEN client requests GET /containers/123/events?type=temperature
- THEN response SHALL contain only events with type 'temperature'

#### Scenario: Filter events by severity
- GIVEN a container with id 123 has events of different severities
- WHEN client requests GET /containers/123/events?severity=critical
- THEN response SHALL contain only events with severity 'critical'

#### Scenario: Paginate events
- GIVEN a container with id 123 has more than 50 events
- WHEN client requests GET /containers/123/events?page=0&size=25
- THEN response SHALL include pagination metadata
- AND only 25 events SHALL be returned per page

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| MON-01 | Container status MUST use lowercase strings: 'active', 'inactive', 'maintenance' | MUST |
| MON-02 | Container list MUST include temperature and humidity from latest telemetry | MUST |
| MON-03 | Container list MUST support filtering by status (lowercase) | MUST |
| MON-04 | Container list MUST support filtering by productType | SHOULD |
| MON-05 | Container list MUST support pagination | MUST |
| MON-06 | Latest telemetry MUST include temperature, humidity, vibration, GPS, door status | MUST |
| MON-07 | Events MUST be ordered by timestamp descending | MUST |
| MON-08 | Events MUST support filtering by type, severity | MUST |
| MON-09 | Events MUST support pagination with configurable page size | SHOULD |
| MON-10 | Battery level MUST be represented as percentage (0-100) | MUST |
| MON-11 | Location coordinates MUST be validated for valid latitude (-90 to 90) and longitude (-180 to 180) | MUST |
| MON-12 | Container lastUpdate timestamp MUST be updated when new telemetry arrives | MUST |
| MON-13 | Deleted containers SHALL no longer appear in list queries | MUST |
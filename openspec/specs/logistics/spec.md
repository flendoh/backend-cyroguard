# Logistics Specification

## Purpose

Logistics handles route management, GPS tracking, and geofencing for CryoGuard's cold chain transport operations. This bounded context provides the route planning and geofence monitoring layer that the Routes page depends on.

## Data Model

### Route Entity
- `id`: string (UUID, immutable identifier)
- `containerId`: string (reference to Container)
- `origin`: string (origin address or location description, max 200 chars)
- `destination`: string (destination address or location description, max 200 chars)
- `status`: 'active' | 'completed' | 'cancelled' (lowercase)
- `startTime`: string (ISO timestamp)
- `estimatedArrival`: string (ISO timestamp)
- `currentLocation`: { lat: number; lng: number } | null

### Geofence Entity
- `id`: string (UUID, immutable identifier)
- `name`: string (human-readable name, 1-100 chars)
- `type`: 'circle' | 'polygon' (lowercase)
- `coordinates`: { lat: number; lng: number }[] (array of coordinate points)
- `radius`: number | null (meters, for circle type only)
- `status`: 'active' | 'inactive' (lowercase)

## API Endpoints

### GET /routes

Returns a list of routes with optional status filtering.

#### Scenario: List all routes
- GIVEN authenticated user
- WHEN client requests GET /routes
- THEN response SHALL contain routes array
- AND each route SHALL include id, containerId, origin, destination, status, startTime, estimatedArrival, currentLocation
- AND routes SHALL be ordered by createdAt descending (newest first)

#### Scenario: Filter routes by status (active)
- GIVEN authenticated user
- WHEN client requests GET /routes?status=active
- THEN response SHALL contain only routes with status 'active'

#### Scenario: Filter routes by status (completed)
- GIVEN authenticated user
- WHEN client requests GET /routes?status=completed
- THEN response SHALL contain only routes with status 'completed'

#### Scenario: Filter routes by container
- GIVEN authenticated user
- WHEN client requests GET /routes?containerId=container-123
- THEN response SHALL contain only routes where containerId equals "container-123"

#### Scenario: List routes with pagination
- GIVEN authenticated user
- WHEN client requests GET /routes?page=0&size=20
- THEN response SHALL include pagination metadata (totalElements, totalPages, currentPage)

---

### GET /routes/{id}

Returns a single route by ID.

#### Scenario: Get route by valid ID
- GIVEN a route with id 123 exists
- WHEN client requests GET /routes/123
- THEN response SHALL contain the route with all fields
- AND response SHALL include origin, destination, status, startTime, estimatedArrival, actualArrival, currentLocation

#### Scenario: Get route with full location history
- GIVEN a route with id 123 exists
- WHEN client requests GET /routes/123
- THEN response MAY include location history if available

#### Scenario: Get non-existent route
- GIVEN no route with id 999 exists
- WHEN client requests GET /routes/999
- THEN the response SHALL return HTTP 404 Not Found

---

### POST /routes

Creates a new route.

#### Scenario: Create route with valid data
- GIVEN authenticated user has ADMIN, OPERATOR, or SUPERVISOR role
- WHEN client submits POST /routes with containerId "container-123", origin "Warehouse A", destination "Hospital B"
- THEN route SHALL be created with status 'active'
- AND startTime SHALL be set to current timestamp
- AND createdAt and updatedAt SHALL be set to current timestamp
- AND the response SHALL include the created route

#### Scenario: Create route sets estimated arrival
- GIVEN authenticated user has ADMIN, OPERATOR, or SUPERVISOR role
- WHEN client submits POST /routes with containerId "container-123", origin, destination, estimatedArrival "2026-05-15T10:00:00Z"
- THEN route estimatedArrival SHALL be set to the provided value

#### Scenario: Create route without required fields
- GIVEN authenticated user has ADMIN, OPERATOR, or SUPERVISOR role
- WHEN client submits POST /routes with missing containerId
- THEN the response SHALL return HTTP 400 Bad Request
- AND validation error SHALL specify containerId is required

#### Scenario: Create route with invalid container
- GIVEN authenticated user has ADMIN, OPERATOR, or SUPERVISOR role
- WHEN client submits POST /routes with containerId "non-existent-container"
- THEN the response SHALL return HTTP 400 Bad Request
- AND validation error SHALL specify container does not exist

#### Scenario: Create route without permission
- GIVEN authenticated user has NGO role
- WHEN client submits POST /routes with valid data
- THEN the response SHALL return HTTP 403 Forbidden

---

### PUT /routes/{id}

Updates an existing route.

#### Scenario: Update route destination
- GIVEN a route with id 123 exists with status 'active'
- WHEN client submits PUT /routes/123 with destination "Hospital C"
- THEN route destination SHALL be updated to "Hospital C"
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update route status to completed
- GIVEN a route with id 123 exists with status 'active'
- WHEN client submits PUT /routes/123 with status "completed"
- THEN route status SHALL be updated to 'completed'
- AND actualArrival SHALL be set to current timestamp

#### Scenario: Update route status to cancelled
- GIVEN a route with id 123 exists with status 'active'
- WHEN client submits PUT /routes/123 with status "cancelled"
- THEN route status SHALL be updated to 'cancelled'
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update route current location
- GIVEN a route with id 123 exists with status 'active'
- WHEN client submits PUT /routes/123 with currentLocation { lat: -33.8688, lng: 151.2093 }
- THEN route currentLocation SHALL be updated
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update non-existent route
- GIVEN no route with id 999 exists
- WHEN client submits PUT /routes/999 with updated data
- THEN the response SHALL return HTTP 404 Not Found

#### Scenario: Update completed route
- GIVEN a route with id 123 exists with status 'completed'
- WHEN client submits PUT /routes/123 with destination "New Destination"
- THEN the response SHALL return HTTP 409 Conflict

---

### DELETE /routes/{id}

Removes a route.

#### Scenario: Delete active route
- GIVEN a route with id 123 exists with status 'active'
- WHEN client requests DELETE /routes/123
- THEN route SHALL be removed
- AND the response SHALL return HTTP 204 No Content
- AND subsequent GET /routes/123 SHALL return HTTP 404

#### Scenario: Delete non-existent route
- GIVEN no route with id 999 exists
- WHEN client requests DELETE /routes/999
- THEN the response SHALL return HTTP 404 Not Found

#### Scenario: Delete already deleted route
- GIVEN a route with id 123 was previously deleted
- WHEN client requests DELETE /routes/123
- THEN the response SHALL return HTTP 404 Not Found

---

### GET /geofences

Returns a list of geofences.

#### Scenario: List all geofences
- GIVEN authenticated user
- WHEN client requests GET /geofences
- THEN response SHALL contain geofences array
- AND each geofence SHALL include id, name, type, coordinates, radius, status
- AND geofences SHALL be ordered by createdAt descending

#### Scenario: Filter geofences by status
- GIVEN authenticated user
- WHEN client requests GET /geofences?status=active
- THEN response SHALL contain only geofences with status 'active'

#### Scenario: List geofences with pagination
- GIVEN authenticated user
- WHEN client requests GET /geofences?page=0&size=20
- THEN response SHALL include pagination metadata

---

### POST /geofences

Creates a new geofence.

#### Scenario: Create circle geofence
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /geofences with name "Warehouse Zone", type "circle", coordinates [{ lat: -33.8688, lng: 151.2093 }], radius 500
- THEN geofence SHALL be created with status 'active'
- AND type SHALL be 'circle'
- AND radius SHALL be 500 (meters)
- AND createdAt and updatedAt SHALL be set to current timestamp
- AND the response SHALL include the created geofence

#### Scenario: Create polygon geofence
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /geofences with name "City Zone", type "polygon", coordinates [{ lat: -33.8, lng: 151.2 }, { lat: -33.9, lng: 151.3 }, { lat: -33.85, lng: 151.15 }]
- THEN geofence SHALL be created with status 'active'
- AND type SHALL be 'polygon'
- AND radius SHALL be null
- AND coordinates SHALL contain the provided polygon points

#### Scenario: Create circle geofence without radius
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /geofences with type "circle" but no radius
- THEN the response SHALL return HTTP 400 Bad Request
- AND validation error SHALL specify radius is required for circle type

#### Scenario: Create geofence with invalid coordinates
- GIVEN authenticated user has ADMIN or OPERATOR role
- WHEN client submits POST /geofences with coordinates [{ lat: 200, lng: 0 }]
- THEN the response SHALL return HTTP 400 Bad Request
- AND validation error SHALL specify latitude must be between -90 and 90

#### Scenario: Create geofence without permission
- GIVEN authenticated user has NGO role
- WHEN client submits POST /geofences with valid data
- THEN the response SHALL return HTTP 403 Forbidden

---

### PUT /geofences/{id}

Updates an existing geofence.

#### Scenario: Update geofence name
- GIVEN a geofence with id 123 exists
- WHEN client submits PUT /geofences/123 with name "Updated Zone"
- THEN geofence name SHALL be updated to "Updated Zone"
- AND updatedAt SHALL be set to current timestamp

#### Scenario: Update geofence radius
- GIVEN a geofence with id 123 exists with type 'circle'
- WHEN client submits PUT /geofences/123 with radius 1000
- THEN geofence radius SHALL be updated to 1000

#### Scenario: Update geofence coordinates
- GIVEN a geofence with id 123 exists
- WHEN client submits PUT /geofences/123 with coordinates [{ lat: -33.9, lng: 151.25 }]
- THEN geofence coordinates SHALL be updated

#### Scenario: Update geofence status to inactive
- GIVEN a geofence with id 123 exists with status 'active'
- WHEN client submits PUT /geofences/123 with status "inactive"
- THEN geofence status SHALL be updated to 'inactive'

#### Scenario: Update non-existent geofence
- GIVEN no geofence with id 999 exists
- WHEN client submits PUT /geofences/999 with updated data
- THEN the response SHALL return HTTP 404 Not Found

---

### DELETE /geofences/{id}

Removes a geofence.

#### Scenario: Delete geofence
- GIVEN a geofence with id 123 exists
- WHEN client requests DELETE /geofences/123
- THEN geofence SHALL be removed
- AND the response SHALL return HTTP 204 No Content
- AND subsequent GET /geofences/123 SHALL return HTTP 404

#### Scenario: Delete non-existent geofence
- GIVEN no geofence with id 999 exists
- WHEN client requests DELETE /geofences/999
- THEN the response SHALL return HTTP 404 Not Found

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| LOG-01 | Route list MUST support filtering by status (active/completed/cancelled) | MUST |
| LOG-02 | Route list MUST support filtering by containerId | SHOULD |
| LOG-03 | Route list MUST support pagination | MUST |
| LOG-04 | Routes MUST be ordered by createdAt descending | MUST |
| LOG-05 | Route creation MUST set startTime to current timestamp | MUST |
| LOG-06 | Route completion MUST set actualArrival to current timestamp | MUST |
| LOG-07 | Route updates MUST NOT be allowed on completed or cancelled routes | MUST |
| LOG-08 | Geofence list MUST support filtering by status | SHOULD |
| LOG-09 | Geofence list MUST support pagination | MUST |
| LOG-10 | Circle geofence creation MUST require radius | MUST |
| LOG-11 | Polygon geofence creation MUST NOT require radius | MUST |
| LOG-12 | Coordinate validation MUST enforce latitude (-90 to 90) and longitude (-180 to 180) | MUST |
| LOG-13 | Geofence type MUST be either 'circle' or 'polygon' | MUST |
| LOG-14 | Route currentLocation MUST be nullable (null when route not started) | MUST |
| LOG-15 | Non-existent resource requests MUST return HTTP 404 | MUST |
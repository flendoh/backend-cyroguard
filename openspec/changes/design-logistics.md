# Design: Logistics Bounded Context

## Context

The Logistics bounded context handles route management, GPS tracking, and geofencing for CryoGuard's cold chain transport operations. It provides the route planning and geofence monitoring layer that the Routes page depends on.

## Package Structure

```
com.example.cryoguard.logistics
├── domain
│   ├── entities
│   │   ├── Route.java              # Route aggregate root
│   │   ├── Geofence.java           # Geofence entity
│   │   └── RouteLocationHistory.java # Location tracking
│   ├── repositories
│   │   ├── RouteRepository.java
│   │   ├── GeofenceRepository.java
│   │   └── RouteLocationHistoryRepository.java
│   ├── valueobjects
│   │   ├── RouteStatus.java        # Enum: ACTIVE, COMPLETED, CANCELLED
│   │   ├── GeofenceType.java       # Enum: CIRCLE, POLYGON
│   │   ├── GeofenceStatus.java     # Enum: ACTIVE, INACTIVE
│   │   └── Coordinate.java         # Embedded value object
│   ├── commands                    # Write operations
│   └── queries                     # Read operations
├── application
│   ├── services
│   │   ├── RouteCommandService.java
│   │   ├── RouteQueryService.java
│   │   ├── GeofenceCommandService.java
│   │   └── GeofenceQueryService.java
│   └── impl
├── infrastructure
│   └── persistence
│       ├── RouteRepository.java
│       ├── GeofenceRepository.java
│       └── RouteLocationHistoryRepository.java
└── presentation
    ├── controllers
    │   ├── RoutesController.java
    │   └── GeofencesController.java
    ├── resources                    # DTOs for API
    ├── assemblers                   # Entity <-> Resource mappers
    └── dtos
```

## Entity Design

### Route Entity

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | PK, auto-gen | Internal ID |
| routeId | String | UUID, unique | External identifier |
| containerId | Long | not null | Reference to Container |
| origin | String | max 200 chars | Origin address/description |
| destination | String | max 200 chars | Destination address/description |
| status | RouteStatus | Enum | active, completed, cancelled |
| startTime | LocalDateTime | nullable | When route started |
| estimatedArrival | LocalDateTime | nullable | ETA |
| actualArrival | LocalDateTime | nullable | When route completed |
| currentLocation | Coordinate | embedded | {lat, lng} or null |
| createdAt | LocalDateTime | not null | Record creation |
| updatedAt | LocalDateTime | not null | Last modification |

**Key JPA Annotations**:
- `@Entity @Table(name = "routes")`
- `@Embedded` for Coordinate value object
- `@Enumerated(EnumType.STRING)` for RouteStatus

### Geofence Entity

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | PK, auto-gen | Internal ID |
| geofenceId | String | UUID, unique | External identifier |
| name | String | 1-100 chars | Human-readable name |
| type | GeofenceType | Enum | circle, polygon |
| coordinates | List<Coordinate> | @ElementCollection | Array of lat/lng points |
| radius | BigDecimal | nullable | Meters, circle type only |
| status | GeofenceStatus | Enum | active, inactive |
| createdAt | LocalDateTime | not null | Record creation |
| updatedAt | LocalDateTime | not null | Last modification |

**Key JPA Annotations**:
- `@Entity @Table(name = "geofences")`
- `@ElementCollection` for coordinates list
- `@CollectionTable` for coordinate storage
- `@Embedded` for each Coordinate

### Coordinate Value Object (Embedded)

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| latitude | BigDecimal | -90 to 90 | Latitude coordinate |
| longitude | BigDecimal | -180 to 180 | Longitude coordinate |

## API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|-------------|----------|
| GET | /routes | List routes with filtering | Query: status, containerId | List<RouteResource> |
| GET | /routes/{id} | Get route by ID | - | RouteResource |
| POST | /routes | Create new route | CreateRouteResource | RouteResource (201) |
| PUT | /routes/{id} | Update route | UpdateRouteResource | RouteResource |
| DELETE | /routes/{id} | Delete route | - | 204 No Content |
| GET | /geofences | List geofences | Query: status | List<GeofenceResource> |
| GET | /geofences/{id} | Get geofence by ID | - | GeofenceResource |
| POST | /geofences | Create geofence | CreateGeofenceResource | GeofenceResource (201) |
| PUT | /geofences/{id} | Update geofence | CreateGeofenceResource | GeofenceResource |
| DELETE | /geofences/{id} | Delete geofence | - | 204 No Content |

## Architecture Decisions

### Decision: Route as aggregate root with location history

**Choice**: Route entity owns RouteLocationHistory relationship
**Alternatives**: Flat route with currentLocation only, separate LocationHistory entity
**Rationale**: Consistent with existing RouteLocationHistory pattern, location history is part of route lifecycle

### Decision: Embedded Coordinate value object

**Choice**: `@Embedded Coordinate` in both Route and Geofence
**Alternatives**: Separate CoordinateEntity, @ElementCollection with @AttributeOverride
**Rationale**: Reusable across entities, validation logic encapsulated, existing pattern in Geofence

### Decision: Geofence coordinates as @ElementCollection

**Choice**: Store coordinates as ElementCollection with separate table
**Alternatives**: Single center point only (existing pattern), JSON column
**Rationale**: Supports both circle (single point + radius) and polygon (multiple points); spec requires array of coordinates

### Decision: Status-based conflict prevention

**Choice**: 409 Conflict when updating completed/cancelled routes
**Alternatives**: Soft deletes, status field ignored on updates
**Rationale**: Clear error message, prevents accidental modifications to historical data

## Service Layer Design

### RouteCommandService
```
createRoute(CreateRouteCommand) → Route
  → Validate container exists
  → Set status=ACTIVE, startTime=now
  → Persist and return

updateRoute(id, UpdateRouteCommand) → Route
  → Validate route exists and status=ACTIVE (409 if not)
  → Update allowed fields
  → Set updatedAt
  → Persist and return

completeRoute(id) → Route
  → Validate route exists and status=ACTIVE (409 if not)
  → Set status=COMPLETED, actualArrival=now
  → Persist and return

cancelRoute(id) → Route
  → Validate route exists and status=ACTIVE (409 if not)
  → Set status=CANCELLED
  → Persist and return
```

### GeofenceCommandService
```
createGeofence(CreateGeofenceCommand) → Geofence
  → Validate coordinates (lat -90 to 90, lng -180 to 180)
  → Validate radius required for CIRCLE type
  → Set status=ACTIVE
  → Persist and return

updateGeofence(id, CreateGeofenceCommand) → Geofence
  → Validate geofence exists
  → Update fields
  → Set updatedAt
  → Persist and return

deleteGeofence(id) → void
  → Validate exists
  → Delete
```

## Key Design Decisions

| Area | Decision | Rationale |
|------|----------|-----------|
| Route immutability | Completed/cancelled routes cannot be modified | LOG-07, data integrity |
| Geofence validation | Latitude ±90, longitude ±180 | LOG-12 coordinate validation |
| Circle radius | Required for circle, null for polygon | LOG-10, LOG-11 |
| Route ordering | Ordered by createdAt descending | LOG-04 |
| Location nullable | currentLocation can be null | LOG-14, route not started |
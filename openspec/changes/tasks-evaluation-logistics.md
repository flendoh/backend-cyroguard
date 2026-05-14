# Implementation Tasks: Evaluation & Logistics Bounded Contexts

## Evaluation Bounded Context

### Domain Entities

- [ ] 1.1 Review/Update Alert entity to match spec (add `acknowledged` and `resolved` boolean fields, use lowercase severity/type)
  - Alert currently uses `AlertStatus status` enum - spec requires separate `acknowledged` and `resolved` boolean fields
  - AlertSeverity enum values are uppercase (CRITICAL, WARNING, INFO) - must change to lowercase (critical, warning, info)
  - AlertType enum values are uppercase (TEMPERATURE_EXCEEDED, etc.) - must change to lowercase (temperature, humidity, vibration, door, geofence)
  - Review field mapping between Alert entity and AlertResource DTO

- [x] 1.2 MonitoringRule entity exists (com.example.cryoguard.evaluation.domain.entities.MonitoringRule)
  - No changes required to entity structure

- [x] 1.3 AlertRepository exists (com.example.cryoguard.evaluation.infrastructure.persistence.AlertRepository)

### Value Objects

- [ ] 1.4 Update AlertSeverity enum to lowercase values
  - Change CRITICAL → CRITICAL (keep uppercase for JPA, but ensure JSON serializes to lowercase)
  - OR use @JsonValue to serialize as lowercase

- [ ] 1.5 Update AlertType enum to lowercase values matching frontend
  - TEMPERATURE_EXCEEDED → temperature
  - HUMIDITY_ALERT → humidity
  - VIBRATION_DETECTED → vibration
  - DOOR_OPENED → door
  - CONNECTION_LOST → door (or add new type)
  - GEOFENCE_VIOLATION → geofence

### Controllers

- [ ] 2.1 Review/Update AlertsController
  - GET /alerts - exists, verify pagination support
  - GET /alerts/{id} - exists
  - PUT /alerts/{id}/acknowledge - currently uses POST, change to PUT per spec
  - PUT /alerts/{id}/resolve - currently uses POST, change to PUT per spec
  - PUT /alerts/{id}/escalate - currently uses POST, change to PUT per spec

- [ ] 2.2 Review/Update MonitoringRulesController
  - GET /monitoring-rules - exists
  - GET /monitoring-rules/{id} - exists
  - PUT /monitoring-rules - currently individual update, change to bulk update per design spec

### DTOs / Resources

- [ ] 3.1 Review/Update AlertResource
  - Add `acknowledged` and `resolved` boolean fields per spec
  - Remove `status` field (replaced by acknowledged/resolved booleans)
  - Verify JSON field naming matches frontend expectations (snake_case)

- [x] 3.2 MonitoringRuleResource exists - no changes needed

### Assemblers

- [ ] 4.1 Update AlertAssembler to map new fields
  - Add mapping for `acknowledged` and `resolved` boolean fields
  - Remove mapping for `status` field

- [x] 4.2 MonitoringRuleAssembler exists - no changes needed

### Service Layer

- [ ] 5.1 Review AlertCommandService/Impl for acknowledge/resolve/escalate logic
  - Ensure 409 Conflict is returned when attempting to acknowledge already acknowledged alerts
  - Ensure 409 Conflict is returned when attempting to resolve already resolved alerts
  - Ensure 409 Conflict is returned when attempting to escalate already critical or resolved alerts
  - Verify info→warning→critical escalation path enforcement

- [ ] 5.2 Review MonitoringRuleCommandService bulk update logic
  - PUT /monitoring-rules should accept array of rule updates

---

## Logistics Bounded Context

### Domain Entities

- [ ] 6.1 Review/Update Route entity to match spec
  - Route uses `name` field but spec requires `origin` and `destination` fields
  - Route uses `startLocation`/`endLocation` but design specifies `origin`/`destination`
  - Route uses `startTime`/`endTime` but spec uses `startTime`/`actualArrival`
  - Route does not have `estimatedArrival` field
  - Route does not have `currentLocation` field (Coordinate embedded)
  - Route does not have `createdAt`/`updatedAt` (uses AuditableAbstractAggregateRoot)

- [ ] 6.2 Review/Update Geofence entity to match spec
  - Geofence uses single center point (centerLatitude, centerLongitude, radiusMeters)
  - Spec requires `coordinates` as array of {lat, lng} points for both circle and polygon
  - GeofenceType enum uses AUTHORIZED_ZONE/RESTRICTED_ZONE but spec requires CIRCLE/POLYGON
  - Coordinate value object needed for embedded {lat, lng} points
  - radius should be nullable for polygon type

- [x] 6.3 RouteRepository exists (com.example.cryoguard.logistics.infrastructure.persistence.RouteRepository)

- [x] 6.4 GeofenceRepository exists (com.example.cryoguard.logistics.infrastructure.persistence.GeofenceRepository)

### Value Objects

- [ ] 7.1 Update RouteStatus enum to lowercase JSON serialization
  - Values are ACTIVE, COMPLETED, CANCELLED - ensure JSON serializes as lowercase

- [ ] 7.2 Update GeofenceType enum to CIRCLE/POLYGON
  - Change AUTHORIZED_ZONE → CIRCLE
  - Change RESTRICTED_ZONE → POLYGON

- [ ] 7.3 Create Coordinate value object (embedded)
  - Fields: latitude (BigDecimal, -90 to 90), longitude (BigDecimal, -180 to 180)
  - Use @Embedded annotation

- [x] 7.4 GeofenceStatus enum exists (ACTIVE, INACTIVE) - verify JSON serialization

### Controllers

- [x] 8.1 RoutesController exists with CRUD operations
  - GET /routes - exists with filtering
  - GET /routes/{id} - exists
  - POST /routes - exists
  - PUT /routes/{id} - exists
  - DELETE /routes/{id} - needs verification (not visible in current file)
  - Location tracking endpoint exists (/routes/{id}/location)

- [x] 8.2 GeofencesController exists with CRUD operations
  - GET /geofences - exists
  - GET /geofences/{id} - exists
  - POST /geofences - exists
  - PUT /geofences/{id} - exists
  - DELETE /geofences/{id} - exists

### DTOs / Resources

- [ ] 9.1 Review/Update RouteResource
  - Add `origin` and `destination` fields (replace name/startLocation/endLocation)
  - Add `estimatedArrival` field
  - Add `actualArrival` field (rename from endTime)
  - Add `currentLocation` field (Coordinate type, nullable)
  - Keep `createdAt`/`updatedAt` from AuditableAbstractAggregateRoot

- [ ] 9.2 Review/Update GeofenceResource
  - Change from single center point to `coordinates` array of {lat, lng}
  - Make `radius` nullable (required only for CIRCLE type)
  - Add `updatedAt` field

- [ ] 9.3 Create Coordinate DTO/resource for embedded location
  - Need CoordinateResource or similar for {lat, lng} embedded value

### Assemblers

- [ ] 10.1 Update RouteAssembler to map new fields
  - Map origin/destination instead of name/startLocation/endLocation
  - Map estimatedArrival, actualArrival, currentLocation

- [ ] 10.2 Update GeofenceAssembler to handle coordinates array
  - Map coordinates List<Coordinate> instead of single center point

### Validation

- [ ] 11.1 Add coordinate validation for Geofence
  - Latitude must be between -90 and 90
  - Longitude must be between -180 and 180
  - Circle geofence must have radius, polygon must not require radius

- [ ] 11.2 Add route status conflict prevention
  - 409 Conflict when updating completed/cancelled routes
  - LOG-07: Route updates MUST NOT be allowed on completed or cancelled routes

### Location Tracking

- [x] 12.1 Location tracking endpoint exists (/routes/{id}/location POST)
- [x] 12.2 RouteLocationHistory entity exists
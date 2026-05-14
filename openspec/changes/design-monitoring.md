# Design: Monitoring Bounded Context

## Technical Approach

Monitoring bounded context handles real-time telemetry collection and container state management. It receives sensor data from IoT devices, stores time-series telemetry readings, and exposes container/alert data to the React frontend via REST endpoints.

## Package Structure

```
com.example.cryoguard.monitoring
├── domain
│   ├── aggregates/
│   │   └── Container.java         # Container aggregate root
│   ├── entities/
│   │   ├── TelemetryReading.java  # Telemetry value object (time-series)
│   │   └── Event.java            # Event entity for alert feed
│   ├── valueobjects/
│   │   ├── ContainerStatus.java   # active|inactive|maintenance
│   │   └── ConnectivityStatus.java
│   ├── commands/
│   │   ├── CreateContainerCommand.java
│   │   ├── UpdateContainerCommand.java
│   │   └── SyncContainerCommand.java
│   └── queries/
│       ├── GetAllContainersQuery.java
│       └── GetContainerByIdQuery.java
├── application
│   ├── ContainerCommandService.java
│   ├── ContainerQueryService.java
│   ├── TelemetryQueryService.java
│   └── impl/
│       ├── ContainerCommandServiceImpl.java
│       ├── ContainerQueryServiceImpl.java
│       └── TelemetryQueryServiceImpl.java
├── infrastructure
│   └── persistence
│       ├── jpa/repositories/
│       │   ├── ContainerRepository.java
│       │   ├── TelemetryRepository.java
│       │   └── EventRepository.java
│       └── seed/
│           └── MonitoringDataSeeder.java
└── presentation
    ├── controllers/
    │   ├── ContainersController.java
    │   └── EventsController.java
    ├── resources/
    │   ├── ContainerResource.java
    │   ├── CreateContainerResource.java
    │   ├── TelemetryReadingResource.java
    │   ├── EventResource.java
    │   └── CreateEventResource.java
    ├── dtos/
    │   ├── ContainerDTO.java
    │   └── TelemetryDTO.java
    └── assemblers/
        ├── ContainerResourceAssembler.java
        ├── TelemetryReadingResourceAssembler.java
        └── EventResourceAssembler.java
```

## Entity Design

### Container.java (existing → modifications needed)

```java
@Entity
@Table(name = "containers")
public class Container extends AuditableAbstractAggregateRoot<Container> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", unique = true, nullable = false)
    private String containerId;  // UUID string, matches frontend id field

    @Column(nullable = false)
    private String name;        // human-readable name

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerStatus status = ContainerStatus.ACTIVE;  // active|inactive|maintenance

    @Embedded
    private GpsLocation currentLocation;  // lat/lng embeddable, nullable

    private BigDecimal currentTemperature;  // nullable, from latest telemetry
    private BigDecimal currentHumidity;     // nullable, from latest telemetry
    private Integer batteryLevel;           // 0-100 percentage
    private LocalDateTime lastUpdate;       // ISO timestamp (maps to lastUpdate)
    private String productType;             // "vaccines", "insulin", "blood"
    private String deviceId;                // hardware identifier
    private Long operatorId;                // assigned operator FK

    // AuditableAbstractAggregateRoot provides: createdAt, updatedAt, deletedAt
}
```

### TelemetryReading.java (existing → extend)

```java
@Entity
@Table(name = "telemetry_readings")
public class TelemetryReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;  // Celsius, -50 to 50

    @Column(precision = 5, scale = 2)
    private BigDecimal humidity;     // percentage, 0-100

    @Column(precision = 5, scale = 2)
    private BigDecimal vibration;   // g-force, 0-10

    @Column(name = "door_open")
    private Boolean doorOpen;

    @Embedded
    @AttributeOverride(name = "latitude", column = @Column(name = "latitude"))
    @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    private GpsCoordinates gps;

    @Column(name = "battery_level")
    private Integer batteryLevel;
}
```

### Event.java (new entity)

```java
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType type;  // temperature|humidity|vibration|door|geofence|battery|offline

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventSeverity severity;  // critical|warning|info

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Boolean acknowledged = false;

    @Column(name = "acknowledged_by")
    private Long acknowledgedBy;

    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;
}
```

## Value Objects

### ContainerStatus enum (replace existing)

```java
public enum ContainerStatus {
    ACTIVE,       // 'active' in API
    INACTIVE,     // 'inactive' in API
    MAINTENANCE   // 'maintenance' in API
}
```

### GpsCoordinates embeddable

```java
@Embeddable
public class GpsCoordinates {
    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;   // -90 to 90

    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;  // -180 to 180
}
```

## API Endpoints

| Method | Path | Description | Auth Required | Roles |
|--------|------|-------------|---------------|-------|
| GET | /containers | List containers (paginated, filterable) | Yes | All |
| POST | /containers | Create new container | Yes | ADMIN, OPERATOR |
| GET | /containers/{id} | Get container by ID | Yes | All |
| PUT | /containers/{id} | Update container | Yes | ADMIN, OPERATOR |
| DELETE | /containers/{id} | Soft delete container | Yes | ADMIN |
| GET | /containers/{id}/telemetry | Get telemetry (latest or history) | Yes | All |
| GET | /containers/{id}/events | Get event feed (filterable, paginated) | Yes | All |

## Architecture Decisions

### Decision: Container aggregates telemetry data

**Choice**: Container entity holds currentTemperature/currentHumidity fields populated from latest TelemetryReading
**Alternatives considered**: Join query on every container list request
**Rationale**: Avoids N+1 queries when listing containers with latest readings. When new telemetry arrives, ContainerCommandService updates container's current readings (MON-02, MON-12).

### Decision: Telemetry as time-series entity, not value object

**Choice**: TelemetryReading is a separate entity with own lifecycle
**Alternatives considered**: Embed telemetry in Container
**Rationale**: Telemetry history is needed for charts/reports. Separate table enables efficient time-range queries. Event sourcing pattern for audit trail.

### Decision: Location as embedded GPS coordinates

**Choice**: Use `@Embedded` GpsCoordinates on Container
**Alternatives considered**: Separate table with FK
**Rationale**: Location is intrinsic to container state. Embeddable simplifies queries (no join needed for current location).

### Decision: Event severity enum values

**Choice**: Store as lowercase strings: 'critical', 'warning', 'info'
**Alternatives considered**: UPPERCASE enums
**Rationale**: Frontend expects lowercase strings per spec (MON-08).

## Data Flow

```
[IoT Device] ──POST /containers/{id}/telemetry──> [ContainersController]
                                                │
                                                ▼
                              [ContainerCommandService.recordTelemetry()]
                                                │
                           ┌────────────────────┴────────────────────┐
                           ▼                                         ▼
              [TelemetryRepository.save(reading)]     [Container.updateCurrentFrom(reading)]
                           │                                         │
                           ▼                                         ▼
                       [Event generated if threshold exceeded]    [Container saved]
                           │                                         │
                           ▼                                         ▼
                  [EventRepository.save(event)]            [Return TelemetryReadingResource]
```

## DTO Mapping

### ContainerResource (maps to frontend Container)

```java
public class ContainerResource {
    private String id;                    // containerId UUID
    private String name;
    private String status;                // lowercase: active|inactive|maintenance
    private GpsLocationDTO currentLocation; // { lat: number, lng: number } | null
    private Double temperature;           // from latest telemetry, nullable
    private Double humidity;              // from latest telemetry, nullable
    private Integer batteryLevel;         // 0-100
    private String lastUpdate;            // ISO timestamp
    private String productType;
    private String deviceId;              // optional
    private String operatorId;            // optional
}
```

### TelemetryReadingResource

```java
public class TelemetryReadingResource {
    private String id;
    private String containerId;
    private Double temperature;            // nullable
    private Double humidity;              // nullable
    private Double vibration;             // nullable
    private GpsCoordinatesDTO gps;        // { lat, lng, accuracy? } | null
    private Boolean doorOpen;
    private String timestamp;             // ISO timestamp
}
```

### EventResource

```java
public class EventResource {
    private String id;
    private String containerId;
    private String type;                  // temperature|humidity|vibration|door|geofence|battery|offline
    private String severity;              // critical|warning|info
    private String message;
    private String timestamp;
    private Boolean acknowledged;
    private String acknowledgedBy;        // optional
    private String acknowledgedAt;        // optional
}
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `monitoring/domain/aggregates/Container.java` | Modify | Add productType, operatorId, currentLocation, lastUpdate |
| `monitoring/domain/valueobjects/ContainerStatus.java` | Replace | Add ACTIVE/INACTIVE/MAINTENANCE values |
| `monitoring/domain/entities/Event.java` | Create | Event entity for alert feed |
| `monitoring/domain/valueobjects/GpsCoordinates.java` | Create | Embeddable for lat/lng |
| `monitoring/domain/entities/EventType.java` | Create | Enum: temperature|humidity|vibration|door|geofence|battery|offline |
| `monitoring/domain/entities/EventSeverity.java` | Create | Enum: critical|warning|info |
| `monitoring/infrastructure/persistence/jpa/repositories/EventRepository.java` | Create | Event queries |
| `monitoring/presentation/controllers/EventsController.java` | Create | GET /containers/{id}/events |
| `monitoring/presentation/resources/EventResource.java` | Create | Event response DTO |
| `monitoring/presentation/assemblers/EventResourceAssembler.java` | Create | Event mapper |
| `monitoring/application/TelemetryQueryService.java` | Modify | Add getTelemetryByContainerId with time range |

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Container.updateStatus() | Mock thresholds, verify status transitions |
| Unit | TelemetryReading creation | Verify GPS validation (-90/90, -180/180) |
| Integration | GET /containers | Test pagination, status filter, productType filter |
| Integration | GET /containers/{id}/telemetry | Test time range queries |
| Integration | GET /containers/{id}/events | Test type/severity filters, pagination |
| E2E | Container lifecycle | Create → Update → Telemetry → Events → Delete |

## Open Questions

- [ ] Should telemetry endpoints support WebSocket for real-time push? (Frontend uses 5-second polling)
- [ ] Do we need to archive old telemetry readings? (Retention policy?)
- [ ] Should events be auto-acknowledged when container status changes to active?
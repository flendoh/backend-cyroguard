package com.example.cryoguard.logistics.domain.entities;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "geofences")
public class Geofence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geofence_id", unique = true, nullable = false)
    private String geofenceId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeofenceType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeofenceStatus status;

    @Column(name = "center_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal centerLatitude;

    @Column(name = "center_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal centerLongitude;

    @Column(name = "radius_meters", nullable = false, precision = 10, scale = 2)
    private BigDecimal radiusMeters;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Geofence() {}

    public Geofence(String geofenceId, String name, GeofenceType type, GeofenceStatus status,
                   BigDecimal centerLatitude, BigDecimal centerLongitude, BigDecimal radiusMeters,
                   LocalDateTime createdAt) {
        this.geofenceId = geofenceId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.radiusMeters = radiusMeters;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGeofenceId() { return geofenceId; }
    public void setGeofenceId(String geofenceId) { this.geofenceId = geofenceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public GeofenceType getType() { return type; }
    public void setType(GeofenceType type) { this.type = type; }

    public GeofenceStatus getStatus() { return status; }
    public void setStatus(GeofenceStatus status) { this.status = status; }

    public BigDecimal getCenterLatitude() { return centerLatitude; }
    public void setCenterLatitude(BigDecimal centerLatitude) { this.centerLatitude = centerLatitude; }

    public BigDecimal getCenterLongitude() { return centerLongitude; }
    public void setCenterLongitude(BigDecimal centerLongitude) { this.centerLongitude = centerLongitude; }

    public BigDecimal getRadiusMeters() { return radiusMeters; }
    public void setRadiusMeters(BigDecimal radiusMeters) { this.radiusMeters = radiusMeters; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean containsPoint(BigDecimal latitude, BigDecimal longitude) {
        double distance = calculateDistance(
            centerLatitude.doubleValue(), centerLongitude.doubleValue(),
            latitude.doubleValue(), longitude.doubleValue()
        );
        return distance <= radiusMeters.doubleValue();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371000;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
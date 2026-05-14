package com.example.cryoguard.logistics.domain.aggregates;

import com.example.cryoguard.logistics.domain.valueobjects.RouteStatus;
import com.example.cryoguard.logistics.domain.entities.RouteCheckpoint;
import com.example.cryoguard.logistics.domain.entities.RouteLocationHistory;
import com.example.cryoguard.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route extends AuditableAbstractAggregateRoot<Route> {

    @Column(name = "route_id", unique = true, nullable = false)
    private String routeId;

    @Column(nullable = false)
    private String name;

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RouteStatus status;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "end_location")
    private String endLocation;

    @Column(name = "distance_km", precision = 10, scale = 2)
    private BigDecimal distanceKm;

    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;

    @Column
    private Integer checkpoints;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteCheckpoint> checkpointsList = new ArrayList<>();

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteLocationHistory> locationHistory = new ArrayList<>();

    public Route() {}

    public Route(String routeId, String name, Long containerId, RouteStatus status,
                 String startLocation, String endLocation, BigDecimal distanceKm,
                 Integer estimatedDurationMinutes, Integer checkpoints, LocalDateTime startTime) {
        this.routeId = routeId;
        this.name = name;
        this.containerId = containerId;
        this.status = status;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distanceKm = distanceKm;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.checkpoints = checkpoints;
        this.startTime = startTime;
    }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getContainerId() { return containerId; }
    public void setContainerId(Long containerId) { this.containerId = containerId; }

    public RouteStatus getStatus() { return status; }
    public void setStatus(RouteStatus status) { this.status = status; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }

    public Integer getEstimatedDurationMinutes() { return estimatedDurationMinutes; }
    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) { this.estimatedDurationMinutes = estimatedDurationMinutes; }

    public Integer getCheckpoints() { return checkpoints; }
    public void setCheckpoints(Integer checkpoints) { this.checkpoints = checkpoints; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public List<RouteCheckpoint> getCheckpointsList() { return checkpointsList; }
    public void setCheckpointsList(List<RouteCheckpoint> checkpointsList) { this.checkpointsList = checkpointsList; }

    public List<RouteLocationHistory> getLocationHistory() { return locationHistory; }
    public void setLocationHistory(List<RouteLocationHistory> locationHistory) { this.locationHistory = locationHistory; }

    public void addCheckpoint(RouteCheckpoint checkpoint) {
        checkpointsList.add(checkpoint);
        checkpoint.setRoute(this);
    }

    public void addLocationHistory(RouteLocationHistory location) {
        locationHistory.add(location);
        location.setRoute(this);
    }

    public void complete() {
        this.status = RouteStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
    }
}
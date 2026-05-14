package com.example.cryoguard.logistics.presentation.resources;

import com.example.cryoguard.logistics.domain.valueobjects.RouteStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RouteResource(
    Long id,
    String routeId,
    String name,
    Long containerId,
    RouteStatus status,
    String origin,
    String destination,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime,
    LocalDateTime estimatedArrival,
    LocalDateTime endTime,
    GpsCoordinatesResource currentLocation
) {
    public record GpsCoordinatesResource(BigDecimal lat, BigDecimal lng) {}
}
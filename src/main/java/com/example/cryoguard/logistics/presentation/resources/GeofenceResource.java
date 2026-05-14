package com.example.cryoguard.logistics.presentation.resources;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GeofenceResource(
    Long id,
    String geofenceId,
    String name,
    GeofenceType type,
    GeofenceStatus status,
    List<CoordinateResource> coordinates,
    BigDecimal radiusMeters,
    LocalDateTime createdAt
) {
    public record CoordinateResource(BigDecimal lat, BigDecimal lng) {}
}
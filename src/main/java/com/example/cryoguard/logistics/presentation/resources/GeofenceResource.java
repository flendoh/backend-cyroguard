package com.example.cryoguard.logistics.presentation.resources;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GeofenceResource(
    Long id,
    String geofenceId,
    String name,
    GeofenceType type,
    GeofenceStatus status,
    BigDecimal centerLatitude,
    BigDecimal centerLongitude,
    BigDecimal radiusMeters,
    LocalDateTime createdAt
) {}
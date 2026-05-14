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
    String startLocation,
    String endLocation,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime,
    LocalDateTime endTime
) {}
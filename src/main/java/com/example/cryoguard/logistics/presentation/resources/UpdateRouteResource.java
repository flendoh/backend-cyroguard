package com.example.cryoguard.logistics.presentation.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateRouteResource(
    String name,
    String startLocation,
    String endLocation,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime
) {}
package com.example.cryoguard.logistics.domain.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateRouteCommand(
    String name,
    String startLocation,
    String endLocation,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime
) {}
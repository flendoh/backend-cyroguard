package com.example.cryoguard.logistics.domain.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRouteCommand(
    String name,
    Long containerId,
    String startLocation,
    String endLocation,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime
) {}
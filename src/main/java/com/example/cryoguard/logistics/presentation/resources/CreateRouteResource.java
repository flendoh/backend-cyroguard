package com.example.cryoguard.logistics.presentation.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRouteResource(
    @NotBlank String name,
    @NotNull Long containerId,
    String startLocation,
    String endLocation,
    BigDecimal distanceKm,
    Integer estimatedDurationMinutes,
    Integer checkpoints,
    LocalDateTime startTime
) {}
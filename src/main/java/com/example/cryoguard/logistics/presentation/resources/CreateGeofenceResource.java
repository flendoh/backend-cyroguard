package com.example.cryoguard.logistics.presentation.resources;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateGeofenceResource(
    @NotBlank String name,
    @NotNull GeofenceType type,
    @NotNull BigDecimal centerLatitude,
    @NotNull BigDecimal centerLongitude,
    @NotNull BigDecimal radiusMeters
) {}
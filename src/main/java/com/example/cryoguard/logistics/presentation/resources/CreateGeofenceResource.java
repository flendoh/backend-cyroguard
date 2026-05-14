package com.example.cryoguard.logistics.presentation.resources;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record CreateGeofenceResource(
    @NotBlank String name,
    @NotNull GeofenceType type,
    @NotNull List<CoordinateResource> coordinates,
    BigDecimal radiusMeters
) {
    public record CoordinateResource(@NotNull BigDecimal lat, @NotNull BigDecimal lng) {}
}
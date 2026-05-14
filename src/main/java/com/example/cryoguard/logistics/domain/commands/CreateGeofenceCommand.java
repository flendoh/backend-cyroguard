package com.example.cryoguard.logistics.domain.commands;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import java.math.BigDecimal;
import java.util.List;

public record CreateGeofenceCommand(
    String name,
    GeofenceType type,
    List<CoordinateCommand> coordinates,
    BigDecimal radiusMeters
) {
    public record CoordinateCommand(BigDecimal latitude, BigDecimal longitude) {}
}
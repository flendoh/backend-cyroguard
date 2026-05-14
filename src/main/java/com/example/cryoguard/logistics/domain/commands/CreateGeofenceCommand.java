package com.example.cryoguard.logistics.domain.commands;

import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import java.math.BigDecimal;

public record CreateGeofenceCommand(
    String name,
    GeofenceType type,
    BigDecimal centerLatitude,
    BigDecimal centerLongitude,
    BigDecimal radiusMeters
) {}
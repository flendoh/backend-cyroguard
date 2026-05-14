package com.example.cryoguard.logistics.presentation.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RouteLocationResource(
    Long id,
    Long routeId,
    LocalDateTime timestamp,
    BigDecimal latitude,
    BigDecimal longitude,
    BigDecimal speed,
    BigDecimal heading
) {}
package com.example.cryoguard.logistics.domain.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecordRouteLocationCommand(
    LocalDateTime timestamp,
    BigDecimal latitude,
    BigDecimal longitude,
    BigDecimal speed,
    BigDecimal heading
) {}
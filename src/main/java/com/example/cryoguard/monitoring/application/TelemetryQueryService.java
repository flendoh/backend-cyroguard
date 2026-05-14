package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;

import java.time.LocalDateTime;
import java.util.List;

public interface TelemetryQueryService {
    List<TelemetryReading> getTelemetryByContainerId(Long containerId);

    List<TelemetryReading> getTelemetryByContainerId(Long containerId, LocalDateTime from, LocalDateTime to);
}
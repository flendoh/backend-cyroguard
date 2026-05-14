package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;

import java.util.List;

public interface TelemetryQueryService {
    List<TelemetryReading> getTelemetryByContainerId(Long containerId);
}
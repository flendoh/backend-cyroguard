package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;
import com.example.cryoguard.monitoring.infrastructure.persistence.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryQueryServiceImpl implements TelemetryQueryService {

    private final TelemetryRepository telemetryRepository;

    @Override
    public List<TelemetryReading> getTelemetryByContainerId(Long containerId) {
        return telemetryRepository.findByContainerIdOrderByTimestampDesc(containerId);
    }
}
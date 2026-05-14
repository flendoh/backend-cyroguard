package com.example.cryoguard.monitoring.infrastructure.persistence;

import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryReading, Long> {
    List<TelemetryReading> findByContainerIdOrderByTimestampDesc(Long containerId);

    List<TelemetryReading> findByContainerIdAndTimestampBetweenOrderByTimestampDesc(
            Long containerId, LocalDateTime from, LocalDateTime to);
}
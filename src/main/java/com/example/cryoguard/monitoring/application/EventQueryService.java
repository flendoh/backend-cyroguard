package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.entities.Event;
import com.example.cryoguard.monitoring.domain.entities.EventSeverity;
import com.example.cryoguard.monitoring.domain.entities.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventQueryService {
    Page<Event> getEventsByContainerId(Long containerId, EventType type, EventSeverity severity, Pageable pageable);
}
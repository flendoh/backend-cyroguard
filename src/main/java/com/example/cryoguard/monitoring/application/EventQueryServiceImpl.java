package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.entities.Event;
import com.example.cryoguard.monitoring.domain.entities.EventSeverity;
import com.example.cryoguard.monitoring.domain.entities.EventType;
import com.example.cryoguard.monitoring.infrastructure.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {

    private final EventRepository eventRepository;

    @Override
    public Page<Event> getEventsByContainerId(Long containerId, EventType type, EventSeverity severity, Pageable pageable) {
        if (type != null && severity != null) {
            return eventRepository.findByContainerIdAndTypeAndSeverityOrderByTimestampDesc(containerId, type, severity, pageable);
        } else if (type != null) {
            return eventRepository.findByContainerIdAndTypeOrderByTimestampDesc(containerId, type, pageable);
        } else if (severity != null) {
            return eventRepository.findByContainerIdAndSeverityOrderByTimestampDesc(containerId, severity, pageable);
        }
        return eventRepository.findByContainerIdOrderByTimestampDesc(containerId, pageable);
    }
}
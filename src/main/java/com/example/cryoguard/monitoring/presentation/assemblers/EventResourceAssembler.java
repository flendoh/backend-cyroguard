package com.example.cryoguard.monitoring.presentation.assemblers;

import com.example.cryoguard.monitoring.domain.entities.Event;
import com.example.cryoguard.monitoring.presentation.resources.EventResource;
import org.springframework.stereotype.Component;

@Component
public class EventResourceAssembler {

    public EventResource toResource(Event event) {
        return new EventResource(
                event.getId(),
                event.getContainerId(),
                event.getType().name().toLowerCase(),
                event.getSeverity().name().toLowerCase(),
                event.getMessage(),
                event.getTimestamp(),
                event.getAcknowledged(),
                event.getAcknowledgedBy(),
                event.getAcknowledgedAt()
        );
    }
}
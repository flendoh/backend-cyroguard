package com.example.cryoguard.monitoring.presentation;

import com.example.cryoguard.monitoring.application.EventQueryService;
import com.example.cryoguard.monitoring.domain.entities.Event;
import com.example.cryoguard.monitoring.domain.entities.EventSeverity;
import com.example.cryoguard.monitoring.domain.entities.EventType;
import com.example.cryoguard.monitoring.presentation.assemblers.EventResourceAssembler;
import com.example.cryoguard.monitoring.presentation.resources.EventResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/containers")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Container event feed operations")
public class EventsController {

    private final EventQueryService eventQueryService;
    private final EventResourceAssembler eventAssembler;

    @GetMapping("/{id}/events")
    @Operation(summary = "Get container events", description = "Returns an event feed for a specific container with optional filtering.")
    public ResponseEntity<Page<EventResource>> getEvents(
            @PathVariable Long id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String severity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        EventType eventType = null;
        EventSeverity eventSeverity = null;

        if (type != null) {
            try {
                eventType = EventType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid type, ignore filter
            }
        }

        if (severity != null) {
            try {
                eventSeverity = EventSeverity.valueOf(severity.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid severity, ignore filter
            }
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = eventQueryService.getEventsByContainerId(id, eventType, eventSeverity, pageable);

        Page<EventResource> resourcePage = events.map(eventAssembler::toResource);
        return ResponseEntity.ok(resourcePage);
    }
}
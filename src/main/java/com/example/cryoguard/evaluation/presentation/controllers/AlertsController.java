package com.example.cryoguard.evaluation.presentation.controllers;

import com.example.cryoguard.evaluation.application.AlertCommandService;
import com.example.cryoguard.evaluation.application.AlertCommandServiceImpl;
import com.example.cryoguard.evaluation.application.AlertQueryService;
import com.example.cryoguard.evaluation.domain.commands.AcknowledgeAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.ResolveAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.presentation.assemblers.AlertAssembler;
import com.example.cryoguard.evaluation.presentation.resources.AlertResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
@Tag(name = "Alerts", description = "Alert and notification management operations")
public class AlertsController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;
    private final AlertAssembler alertAssembler;

    @GetMapping
    @Operation(summary = "Get all alerts", description = "Retrieves all alerts with optional filtering by status, severity, or container ID.")
    public ResponseEntity<?> getAllAlerts(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long containerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<Alert> alerts;

        if (severity != null || status != null) {
            alerts = alertQueryService.getAlertsByFilters(severity, status, pageable);
        } else if (containerId != null) {
            alerts = alertQueryService.getAlertsPage(pageable);
        } else {
            alerts = alertQueryService.getAlertsPage(pageable);
        }

        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID", description = "Retrieves a specific alert by its unique identifier.")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long id) {
        return alertQueryService.getAlertById(id)
                .map(alert -> ResponseEntity.ok(alertAssembler.toResource(alert)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge alert", description = "Marks an alert as acknowledged by a user.")
    public ResponseEntity<AlertResource> acknowledgeAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {
        try {
            AcknowledgeAlertCommand command = new AcknowledgeAlertCommand(id, userId);
            Alert alert = alertCommandService.acknowledgeAlert(command);
            return ResponseEntity.ok(alertAssembler.toResource(alert));
        } catch (AlertCommandServiceImpl.AlertAlreadyAcknowledgedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve alert", description = "Marks an alert as resolved by a user.")
    public ResponseEntity<AlertResource> resolveAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {
        try {
            ResolveAlertCommand command = new ResolveAlertCommand(id, userId);
            Alert alert = alertCommandService.resolveAlert(command);
            return ResponseEntity.ok(alertAssembler.toResource(alert));
        } catch (AlertCommandServiceImpl.AlertAlreadyResolvedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}/escalate")
    @Operation(summary = "Escalate alert", description = "Escalates an alert to a higher severity level.")
    public ResponseEntity<AlertResource> escalateAlert(@PathVariable Long id) {
        try {
            Alert alert = alertCommandService.escalateAlert(id);
            return ResponseEntity.ok(alertAssembler.toResource(alert));
        } catch (AlertCommandServiceImpl.AlertAlreadyCriticalException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (AlertCommandServiceImpl.AlertResolvedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
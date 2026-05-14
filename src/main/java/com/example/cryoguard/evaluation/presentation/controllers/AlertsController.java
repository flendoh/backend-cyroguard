package com.example.cryoguard.evaluation.presentation.controllers;

import com.example.cryoguard.evaluation.application.AlertCommandService;
import com.example.cryoguard.evaluation.application.AlertQueryService;
import com.example.cryoguard.evaluation.domain.commands.AcknowledgeAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.ResolveAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.EscalateAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import com.example.cryoguard.evaluation.presentation.assemblers.AlertAssembler;
import com.example.cryoguard.evaluation.presentation.resources.AlertResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<AlertResource>> getAllAlerts(
            @RequestParam(required = false) AlertStatus status,
            @RequestParam(required = false) AlertSeverity severity,
            @RequestParam(required = false) Long containerId) {

        List<Alert> alerts;

        if (status != null) {
            alerts = alertQueryService.getAlertsByStatus(status);
        } else if (containerId != null) {
            alerts = alertQueryService.getAlertsByContainer(containerId);
        } else {
            alerts = alertQueryService.getAllAlerts();
        }

        if (severity != null) {
            alerts = alerts.stream()
                    .filter(a -> a.getSeverity() == severity)
                    .collect(Collectors.toList());
        }

        List<AlertResource> resources = alerts.stream()
                .map(alertAssembler::toResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID", description = "Retrieves a specific alert by its unique identifier.")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long id) {
        return alertQueryService.getAlertById(id)
                .map(alert -> ResponseEntity.ok(alertAssembler.toResource(alert)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge alert", description = "Marks an alert as acknowledged by a user.")
    public ResponseEntity<AlertResource> acknowledgeAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {
        AcknowledgeAlertCommand command = new AcknowledgeAlertCommand(id, userId);
        Alert alert = alertCommandService.acknowledgeAlert(command);
        return ResponseEntity.ok(alertAssembler.toResource(alert));
    }

    @PostMapping("/{id}/resolve")
    @Operation(summary = "Resolve alert", description = "Marks an alert as resolved by a user.")
    public ResponseEntity<AlertResource> resolveAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {
        ResolveAlertCommand command = new ResolveAlertCommand(id, userId);
        Alert alert = alertCommandService.resolveAlert(command);
        return ResponseEntity.ok(alertAssembler.toResource(alert));
    }

    @PostMapping("/{id}/escalate")
    @Operation(summary = "Escalate alert", description = "Escalates an alert to a higher severity level.")
    public ResponseEntity<AlertResource> escalateAlert(
            @PathVariable Long id,
            @RequestParam AlertSeverity newSeverity) {
        EscalateAlertCommand command = new EscalateAlertCommand(id, newSeverity);
        Alert alert = alertCommandService.escalateAlert(command);
        return ResponseEntity.ok(alertAssembler.toResource(alert));
    }
}
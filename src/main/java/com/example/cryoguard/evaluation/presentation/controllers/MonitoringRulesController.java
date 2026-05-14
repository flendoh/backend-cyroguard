package com.example.cryoguard.evaluation.presentation.controllers;

import com.example.cryoguard.evaluation.application.MonitoringRuleCommandService;
import com.example.cryoguard.evaluation.application.MonitoringRuleQueryService;
import com.example.cryoguard.evaluation.domain.commands.CreateMonitoringRuleCommand;
import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.assemblers.MonitoringRuleAssembler;
import com.example.cryoguard.evaluation.presentation.resources.CreateMonitoringRuleResource;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/monitoring-rules")
@RequiredArgsConstructor
@Tag(name = "Monitoring Rules", description = "Monitoring rule management operations")
public class MonitoringRulesController {

    private final MonitoringRuleCommandService monitoringRuleCommandService;
    private final MonitoringRuleQueryService monitoringRuleQueryService;
    private final MonitoringRuleAssembler monitoringRuleAssembler;

    @GetMapping
    @Operation(summary = "Get all monitoring rules", description = "Retrieves all monitoring rules with optional filtering by container ID or active status.")
    public ResponseEntity<List<MonitoringRuleResource>> getAllRules(
            @RequestParam(required = false) Long containerId,
            @RequestParam(required = false) Boolean active) {

        List<MonitoringRule> rules;

        if (containerId != null) {
            rules = monitoringRuleQueryService.getRulesByContainer(containerId);
        } else if (Boolean.TRUE.equals(active)) {
            rules = monitoringRuleQueryService.getActiveRules();
        } else {
            rules = monitoringRuleQueryService.getAllRules();
        }

        List<MonitoringRuleResource> resources = rules.stream()
                .map(monitoringRuleAssembler::toResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get monitoring rule by ID", description = "Retrieves a specific monitoring rule by its unique identifier.")
    public ResponseEntity<MonitoringRuleResource> getRuleById(@PathVariable Long id) {
        return monitoringRuleQueryService.getRuleById(id)
                .map(rule -> ResponseEntity.ok(monitoringRuleAssembler.toResource(rule)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create monitoring rule", description = "Creates a new monitoring rule with specified thresholds for temperature, humidity, vibration, and door open duration.")
    public ResponseEntity<MonitoringRuleResource> createRule(
            @RequestBody CreateMonitoringRuleResource resource) {
        CreateMonitoringRuleCommand command = new CreateMonitoringRuleCommand();
        command.setContainerId(resource.getContainerId());
        command.setTemperatureMin(resource.getTemperatureMin());
        command.setTemperatureMax(resource.getTemperatureMax());
        command.setTemperatureWarningOffset(resource.getTemperatureWarningOffset());
        command.setHumidityMin(resource.getHumidityMin());
        command.setHumidityMax(resource.getHumidityMax());
        command.setHumidityWarningOffset(resource.getHumidityWarningOffset());
        command.setMaxVibration(resource.getMaxVibration());
        command.setCriticalVibration(resource.getCriticalVibration());
        command.setMaxDoorOpenMinutes(resource.getMaxDoorOpenMinutes());
        command.setActive(resource.getActive());

        MonitoringRule rule = monitoringRuleCommandService.createRule(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(monitoringRuleAssembler.toResource(rule));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update monitoring rule", description = "Updates an existing monitoring rule with new threshold values.")
    public ResponseEntity<MonitoringRuleResource> updateRule(
            @PathVariable Long id,
            @RequestBody CreateMonitoringRuleResource resource) {
        CreateMonitoringRuleCommand command = new CreateMonitoringRuleCommand();
        command.setContainerId(resource.getContainerId());
        command.setTemperatureMin(resource.getTemperatureMin());
        command.setTemperatureMax(resource.getTemperatureMax());
        command.setTemperatureWarningOffset(resource.getTemperatureWarningOffset());
        command.setHumidityMin(resource.getHumidityMin());
        command.setHumidityMax(resource.getHumidityMax());
        command.setHumidityWarningOffset(resource.getHumidityWarningOffset());
        command.setMaxVibration(resource.getMaxVibration());
        command.setCriticalVibration(resource.getCriticalVibration());
        command.setMaxDoorOpenMinutes(resource.getMaxDoorOpenMinutes());
        command.setActive(resource.getActive());

        MonitoringRule rule = monitoringRuleCommandService.updateRule(id, command);
        return ResponseEntity.ok(monitoringRuleAssembler.toResource(rule));
    }
}
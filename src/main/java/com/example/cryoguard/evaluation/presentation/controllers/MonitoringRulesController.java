package com.example.cryoguard.evaluation.presentation.controllers;

import com.example.cryoguard.evaluation.application.MonitoringRuleCommandService;
import com.example.cryoguard.evaluation.application.MonitoringRuleQueryService;
import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.assemblers.MonitoringRuleAssembler;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleResource;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleUpdateResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "Get all monitoring rules", description = "Retrieves all monitoring rules.")
    public ResponseEntity<List<MonitoringRuleResource>> getAllRules() {
        List<MonitoringRule> rules = monitoringRuleQueryService.getAllRules();
        List<MonitoringRuleResource> resources = rules.stream()
                .map(monitoringRuleAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get monitoring rule by ID", description = "Retrieves a specific monitoring rule by its unique identifier.")
    public ResponseEntity<MonitoringRuleResource> getRuleById(@PathVariable Long id) {
        return monitoringRuleCommandService.getRuleById(id)
                .map(rule -> ResponseEntity.ok(monitoringRuleAssembler.toResource(rule)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @Operation(summary = "Update monitoring rules", description = "Updates multiple monitoring rules in a batch operation.")
    public ResponseEntity<List<MonitoringRuleResource>> updateRules(
            @RequestBody List<MonitoringRuleUpdateResource> updates) {
        try {
            List<MonitoringRule> rules = monitoringRuleCommandService.updateRules(updates);
            List<MonitoringRuleResource> resources = rules.stream()
                    .map(monitoringRuleAssembler::toResource)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resources);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
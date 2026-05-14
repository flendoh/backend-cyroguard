package com.example.cryoguard.evaluation.presentation.assemblers;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleResource;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MonitoringRuleAssembler {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<MonitoringRuleResource> toResource(List<MonitoringRule> rules) {
        List<MonitoringRuleResource> resources = new ArrayList<>();

        for (MonitoringRule rule : rules) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName(getNameForParameter(rule.getParameter()));
            resource.setParameter(rule.getParameter());
            resource.setValue(rule.getThresholdValue());
            resource.setUnit(rule.getUnit());
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }

        return resources;
    }

    public MonitoringRuleResource toResource(MonitoringRule rule) {
        if (rule == null) {
            return null;
        }
        MonitoringRuleResource resource = new MonitoringRuleResource();
        resource.setId(rule.getId());
        resource.setName(getNameForParameter(rule.getParameter()));
        resource.setParameter(rule.getParameter());
        resource.setValue(rule.getThresholdValue());
        resource.setUnit(rule.getUnit());
        if (rule.getUpdatedAt() != null) {
            resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
        }
        return resource;
    }

    private String getNameForParameter(String parameter) {
        return switch (parameter) {
            case "temperature_min" -> "Temperature Min";
            case "temperature_max" -> "Temperature Max";
            case "humidity_min" -> "Humidity Min";
            case "humidity_max" -> "Humidity Max";
            case "vibration_threshold" -> "Vibration Threshold";
            default -> parameter;
        };
    }
}
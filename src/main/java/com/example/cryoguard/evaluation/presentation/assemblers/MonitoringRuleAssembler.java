package com.example.cryoguard.evaluation.presentation.assemblers;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleResource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MonitoringRuleAssembler {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<MonitoringRuleResource> toResource(List<MonitoringRule> rules) {
        List<MonitoringRuleResource> resources = new ArrayList<>();

        for (MonitoringRule rule : rules) {
            addResourcesFromRule(rule, resources);
        }

        return resources;
    }

    private void addResourcesFromRule(MonitoringRule rule, List<MonitoringRuleResource> resources) {
        if (rule.getTemperatureMin() != null) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName("Temperature Min");
            resource.setParameter("temperature_min");
            resource.setValue(rule.getTemperatureMin());
            resource.setUnit("°C");
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }

        if (rule.getTemperatureMax() != null) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName("Temperature Max");
            resource.setParameter("temperature_max");
            resource.setValue(rule.getTemperatureMax());
            resource.setUnit("°C");
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }

        if (rule.getHumidityMin() != null) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName("Humidity Min");
            resource.setParameter("humidity_min");
            resource.setValue(rule.getHumidityMin());
            resource.setUnit("%");
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }

        if (rule.getHumidityMax() != null) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName("Humidity Max");
            resource.setParameter("humidity_max");
            resource.setValue(rule.getHumidityMax());
            resource.setUnit("%");
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }

        if (rule.getMaxVibration() != null) {
            MonitoringRuleResource resource = new MonitoringRuleResource();
            resource.setId(rule.getId());
            resource.setName("Vibration Threshold");
            resource.setParameter("vibration_threshold");
            resource.setValue(rule.getMaxVibration());
            resource.setUnit("g");
            if (rule.getUpdatedAt() != null) {
                resource.setUpdatedAt(rule.getUpdatedAt().format(ISO_FORMATTER));
            }
            resources.add(resource);
        }
    }

    public MonitoringRuleResource toResource(MonitoringRule rule) {
        List<MonitoringRuleResource> resources = toResource(List.of(rule));
        return resources.isEmpty() ? null : resources.get(0);
    }
}
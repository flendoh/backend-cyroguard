package com.example.cryoguard.evaluation.presentation.assemblers;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleResource;
import org.springframework.stereotype.Component;

@Component
public class MonitoringRuleAssembler {

    public MonitoringRuleResource toResource(MonitoringRule rule) {
        MonitoringRuleResource resource = new MonitoringRuleResource();
        resource.setId(rule.getId());
        resource.setContainerId(rule.getContainerId());
        resource.setTemperatureMin(rule.getTemperatureMin());
        resource.setTemperatureMax(rule.getTemperatureMax());
        resource.setTemperatureWarningOffset(rule.getTemperatureWarningOffset());
        resource.setHumidityMin(rule.getHumidityMin());
        resource.setHumidityMax(rule.getHumidityMax());
        resource.setHumidityWarningOffset(rule.getHumidityWarningOffset());
        resource.setMaxVibration(rule.getMaxVibration());
        resource.setCriticalVibration(rule.getCriticalVibration());
        resource.setMaxDoorOpenMinutes(rule.getMaxDoorOpenMinutes());
        resource.setActive(rule.getActive());
        return resource;
    }
}
package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.CreateMonitoringRuleCommand;
import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.infrastructure.persistence.MonitoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringRuleCommandServiceImpl implements MonitoringRuleCommandService {

    private final MonitoringRuleRepository monitoringRuleRepository;

    @Override
    @Transactional
    public MonitoringRule createRule(CreateMonitoringRuleCommand command) {
        MonitoringRule rule = new MonitoringRule();
        rule.setContainerId(command.getContainerId());
        rule.setTemperatureMin(command.getTemperatureMin());
        rule.setTemperatureMax(command.getTemperatureMax());
        rule.setTemperatureWarningOffset(command.getTemperatureWarningOffset());
        rule.setHumidityMin(command.getHumidityMin());
        rule.setHumidityMax(command.getHumidityMax());
        rule.setHumidityWarningOffset(command.getHumidityWarningOffset());
        rule.setMaxVibration(command.getMaxVibration());
        rule.setCriticalVibration(command.getCriticalVibration());
        rule.setMaxDoorOpenMinutes(command.getMaxDoorOpenMinutes());
        rule.setActive(command.getActive() != null ? command.getActive() : true);
        return monitoringRuleRepository.save(rule);
    }

    @Override
    @Transactional
    public MonitoringRule updateRule(Long id, CreateMonitoringRuleCommand command) {
        MonitoringRule rule = monitoringRuleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Monitoring rule not found: " + id));
        rule.setContainerId(command.getContainerId());
        rule.setTemperatureMin(command.getTemperatureMin());
        rule.setTemperatureMax(command.getTemperatureMax());
        rule.setTemperatureWarningOffset(command.getTemperatureWarningOffset());
        rule.setHumidityMin(command.getHumidityMin());
        rule.setHumidityMax(command.getHumidityMax());
        rule.setHumidityWarningOffset(command.getHumidityWarningOffset());
        rule.setMaxVibration(command.getMaxVibration());
        rule.setCriticalVibration(command.getCriticalVibration());
        rule.setMaxDoorOpenMinutes(command.getMaxDoorOpenMinutes());
        if (command.getActive() != null) {
            rule.setActive(command.getActive());
        }
        return monitoringRuleRepository.save(rule);
    }

    @Override
    @Transactional
    public void deleteRule(Long id) {
        monitoringRuleRepository.deleteById(id);
    }

    @Override
    public Optional<MonitoringRule> getRuleById(Long id) {
        return monitoringRuleRepository.findById(id);
    }

    @Override
    public List<MonitoringRule> getAllRules() {
        return monitoringRuleRepository.findAll();
    }
}
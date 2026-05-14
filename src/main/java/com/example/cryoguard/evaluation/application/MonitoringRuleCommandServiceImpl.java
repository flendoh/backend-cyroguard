package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.infrastructure.persistence.MonitoringRuleRepository;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleUpdateResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringRuleCommandServiceImpl implements MonitoringRuleCommandService {

    private final MonitoringRuleRepository monitoringRuleRepository;

    @Override
    @Transactional
    public MonitoringRule createRule(MonitoringRule rule) {
        rule.setUpdatedAt(LocalDateTime.now());
        return monitoringRuleRepository.save(rule);
    }

    @Override
    @Transactional
    public List<MonitoringRule> updateRules(List<MonitoringRuleUpdateResource> updates) {
        for (MonitoringRuleUpdateResource update : updates) {
            validateValue(update.getParameter(), update.getValue());
        }

        List<MonitoringRule> existingRules = monitoringRuleRepository.findAll();

        if (existingRules.isEmpty()) {
            MonitoringRule newRule = new MonitoringRule();
            newRule.setContainerId(1L);
            newRule.setActive(true);
            applyUpdatesToRule(newRule, updates);
            newRule.setUpdatedAt(LocalDateTime.now());
            monitoringRuleRepository.save(newRule);
        } else {
            MonitoringRule rule = existingRules.get(0);
            applyUpdatesToRule(rule, updates);
            rule.setUpdatedAt(LocalDateTime.now());
            monitoringRuleRepository.save(rule);
        }

        return monitoringRuleRepository.findAll();
    }

    private void applyUpdatesToRule(MonitoringRule rule, List<MonitoringRuleUpdateResource> updates) {
        for (MonitoringRuleUpdateResource update : updates) {
            String param = update.getParameter();
            BigDecimal value = update.getValue();

            switch (param) {
                case "temperature_min" -> rule.setTemperatureMin(value);
                case "temperature_max" -> rule.setTemperatureMax(value);
                case "humidity_min" -> rule.setHumidityMin(value);
                case "humidity_max" -> rule.setHumidityMax(value);
                case "vibration_threshold" -> rule.setMaxVibration(value);
            }
        }
    }

    private void validateValue(String parameter, BigDecimal value) {
        if (value == null) {
            return;
        }

        switch (parameter) {
            case "temperature_min", "temperature_max" -> {
                if (value.compareTo(new BigDecimal("-100")) < 0 || value.compareTo(new BigDecimal("100")) > 0) {
                    throw new IllegalArgumentException("Temperature value must be between -100 and 100");
                }
            }
            case "humidity_min", "humidity_max" -> {
                if (value.compareTo(new BigDecimal("0")) < 0 || value.compareTo(new BigDecimal("100")) > 0) {
                    throw new IllegalArgumentException("Humidity value must be between 0 and 100");
                }
            }
            case "vibration_threshold" -> {
                if (value.compareTo(new BigDecimal("0")) < 0 || value.compareTo(new BigDecimal("100")) > 0) {
                    throw new IllegalArgumentException("Vibration threshold must be between 0 and 100");
                }
            }
        }
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
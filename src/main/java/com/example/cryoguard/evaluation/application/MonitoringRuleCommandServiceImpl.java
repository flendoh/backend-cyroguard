package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.infrastructure.persistence.MonitoringRuleRepository;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleUpdateResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MonitoringRuleCommandServiceImpl implements MonitoringRuleCommandService {

    private final MonitoringRuleRepository monitoringRuleRepository;

    private static final Map<String, String> PARAMETER_UNITS = Map.of(
            "temperature_min", "°C",
            "temperature_max", "°C",
            "humidity_min", "%",
            "humidity_max", "%",
            "vibration_threshold", "g"
    );

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

        Map<String, MonitoringRule> existingRulesMap = new HashMap<>();
        for (MonitoringRule rule : monitoringRuleRepository.findAll()) {
            existingRulesMap.put(rule.getParameter(), rule);
        }

        for (MonitoringRuleUpdateResource update : updates) {
            String param = update.getParameter();
            BigDecimal value = update.getValue();
            String unit = PARAMETER_UNITS.get(param);

            MonitoringRule rule = existingRulesMap.get(param);
            if (rule == null) {
                rule = new MonitoringRule();
                rule.setContainerId(1L);
                rule.setParameter(param);
                rule.setUnit(unit);
                rule.setActive(true);
            }
            rule.setThresholdValue(value);
            rule.setUpdatedAt(LocalDateTime.now());
            monitoringRuleRepository.save(rule);
        }

        return monitoringRuleRepository.findAll();
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
                if (value.compareTo(new BigDecimal("0")) < 0 || value.compareTo(new BigDecimal("100")) > 100) {
                    throw new IllegalArgumentException("Humidity value must be between 0 and 100");
                }
            }
            case "vibration_threshold" -> {
                if (value.compareTo(new BigDecimal("0")) < 0 || value.compareTo(new BigDecimal("100")) > 100) {
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
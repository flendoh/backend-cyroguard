package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.presentation.resources.MonitoringRuleUpdateResource;

import java.util.List;
import java.util.Optional;

public interface MonitoringRuleCommandService {

    MonitoringRule createRule(MonitoringRule rule);

    List<MonitoringRule> updateRules(List<MonitoringRuleUpdateResource> updates);

    Optional<MonitoringRule> getRuleById(Long id);

    List<MonitoringRule> getAllRules();
}
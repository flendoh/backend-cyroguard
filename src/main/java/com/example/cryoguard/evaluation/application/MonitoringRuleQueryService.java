package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;

import java.util.List;
import java.util.Optional;

public interface MonitoringRuleQueryService {

    Optional<MonitoringRule> getRuleById(Long id);

    List<MonitoringRule> getRulesByContainer(Long containerId);

    List<MonitoringRule> getActiveRules();

    List<MonitoringRule> getAllRules();
}
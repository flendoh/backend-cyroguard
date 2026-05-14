package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.CreateMonitoringRuleCommand;
import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;

import java.util.List;
import java.util.Optional;

public interface MonitoringRuleCommandService {

    MonitoringRule createRule(CreateMonitoringRuleCommand command);

    MonitoringRule updateRule(Long id, CreateMonitoringRuleCommand command);

    void deleteRule(Long id);

    Optional<MonitoringRule> getRuleById(Long id);

    List<MonitoringRule> getAllRules();
}
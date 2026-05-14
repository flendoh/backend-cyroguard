package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.infrastructure.persistence.MonitoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoringRuleQueryServiceImpl implements MonitoringRuleQueryService {

    private final MonitoringRuleRepository monitoringRuleRepository;

    @Override
    public Optional<MonitoringRule> getRuleById(Long id) {
        return monitoringRuleRepository.findById(id);
    }

    @Override
    public List<MonitoringRule> getRulesByContainer(Long containerId) {
        return monitoringRuleRepository.findByContainerId(containerId);
    }

    @Override
    public List<MonitoringRule> getActiveRules() {
        return monitoringRuleRepository.findByActiveTrue();
    }

    @Override
    public List<MonitoringRule> getAllRules() {
        return monitoringRuleRepository.findAll();
    }
}
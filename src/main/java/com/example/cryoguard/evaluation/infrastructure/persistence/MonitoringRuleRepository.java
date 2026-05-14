package com.example.cryoguard.evaluation.infrastructure.persistence;

import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoringRuleRepository extends JpaRepository<MonitoringRule, Long> {

    List<MonitoringRule> findByContainerId(Long containerId);

    List<MonitoringRule> findByContainerIdAndActiveTrue(Long containerId);

    List<MonitoringRule> findByActiveTrue();
}
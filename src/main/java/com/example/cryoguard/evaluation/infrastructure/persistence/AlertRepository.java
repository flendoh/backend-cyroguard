package com.example.cryoguard.evaluation.infrastructure.persistence;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByAlertId(String alertId);

    List<Alert> findByContainerId(Long containerId);

    List<Alert> findByStatus(AlertStatus status);

    List<Alert> findByContainerIdAndStatus(Long containerId, AlertStatus status);

    List<Alert> findBySeverity(String severity);
}
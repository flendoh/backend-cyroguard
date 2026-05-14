package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {

    Optional<Alert> getAlertById(Long alertId);

    List<Alert> getAlertsByContainer(Long containerId);

    List<Alert> getAlertsByStatus(AlertStatus status);

    List<Alert> getAllAlerts();
}
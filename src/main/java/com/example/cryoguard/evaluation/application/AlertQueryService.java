package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {

    Optional<Alert> getAlertById(Long alertId);

    List<Alert> getAlertsByContainer(Long containerId);

    List<Alert> getAlertsByStatus(Boolean resolved);

    List<Alert> getAllAlerts();

    Page<Alert> getAlertsPage(Pageable pageable);

    Page<Alert> getAlertsByFilters(String severity, String status, Pageable pageable);
}
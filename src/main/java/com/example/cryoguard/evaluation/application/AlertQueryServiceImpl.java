package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import com.example.cryoguard.evaluation.infrastructure.persistence.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    @Override
    public Optional<Alert> getAlertById(Long alertId) {
        return alertRepository.findById(alertId);
    }

    @Override
    public List<Alert> getAlertsByContainer(Long containerId) {
        return alertRepository.findByContainerId(containerId);
    }

    @Override
    public List<Alert> getAlertsByStatus(AlertStatus status) {
        return alertRepository.findByStatus(status);
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
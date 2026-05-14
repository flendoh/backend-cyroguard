package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.infrastructure.persistence.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Alert> getAlertsByStatus(Boolean resolved) {
        if (Boolean.TRUE.equals(resolved)) {
            return alertRepository.findByResolvedTrueOrderByTimestampDesc();
        } else {
            return alertRepository.findByResolvedFalseOrderByTimestampDesc();
        }
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public Page<Alert> getAlertsPage(Pageable pageable) {
        return alertRepository.findAll(pageable);
    }

    @Override
    public Page<Alert> getAlertsByFilters(String severity, String status, Pageable pageable) {
        return alertRepository.findByFilters(severity, status, pageable);
    }
}
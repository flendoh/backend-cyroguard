package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.AcknowledgeAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.CreateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.EscalateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.ResolveAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import com.example.cryoguard.evaluation.infrastructure.persistence.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;
    private final AtomicLong alertSequence = new AtomicLong(1);

    @Override
    @Transactional
    public Alert createAlert(CreateAlertCommand command) {
        Alert alert = new Alert();
        alert.setAlertId(generateAlertId());
        alert.setContainerId(command.getContainerId());
        alert.setSeverity(command.getSeverity());
        alert.setAlertType(command.getAlertType());
        alert.setMessage(command.getMessage());
        alert.setTimestamp(LocalDateTime.now());
        alert.setStatus(AlertStatus.OPEN);
        alert.setLatitude(command.getLatitude());
        alert.setLongitude(command.getLongitude());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert acknowledgeAlert(AcknowledgeAlertCommand command) {
        Alert alert = alertRepository.findById(command.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + command.getAlertId()));
        alert.setStatus(AlertStatus.ACKNOWLEDGED);
        alert.setAcknowledgedBy(command.getUserId());
        alert.setAcknowledgedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert resolveAlert(ResolveAlertCommand command) {
        Alert alert = alertRepository.findById(command.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + command.getAlertId()));
        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedBy(command.getUserId());
        alert.setResolvedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert escalateAlert(EscalateAlertCommand command) {
        Alert alert = alertRepository.findById(command.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + command.getAlertId()));
        alert.setSeverity(command.getNewSeverity());
        return alertRepository.save(alert);
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    private String generateAlertId() {
        return "ALT-" + String.format("%03d", alertSequence.getAndIncrement());
    }
}
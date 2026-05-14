package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.AcknowledgeAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.CreateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.EscalateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.ResolveAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertType;
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
        alert.setAcknowledged(false);
        alert.setResolved(false);
        alert.setLatitude(command.getLatitude());
        alert.setLongitude(command.getLongitude());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert acknowledgeAlert(AcknowledgeAlertCommand command) {
        Alert alert = alertRepository.findById(command.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + command.getAlertId()));

        if (Boolean.TRUE.equals(alert.getAcknowledged())) {
            throw new AlertAlreadyAcknowledgedException("Alert already acknowledged: " + command.getAlertId());
        }

        alert.setAcknowledged(true);
        alert.setAcknowledgedBy(command.getUserId());
        alert.setAcknowledgedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert resolveAlert(ResolveAlertCommand command) {
        Alert alert = alertRepository.findById(command.getAlertId())
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + command.getAlertId()));

        if (Boolean.TRUE.equals(alert.getResolved())) {
            throw new AlertAlreadyResolvedException("Alert already resolved: " + command.getAlertId());
        }

        if (!Boolean.TRUE.equals(alert.getAcknowledged())) {
            alert.setAcknowledged(true);
            alert.setAcknowledgedBy(command.getUserId());
            alert.setAcknowledgedAt(LocalDateTime.now());
        }

        alert.setResolved(true);
        alert.setResolvedBy(command.getUserId());
        alert.setResolvedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @Override
    @Transactional
    public Alert escalateAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + alertId));

        if (Boolean.TRUE.equals(alert.getResolved())) {
            throw new AlertResolvedException("Cannot escalate resolved alert: " + alertId);
        }

        AlertSeverity currentSeverity = alert.getSeverity();
        AlertSeverity nextSeverity = currentSeverity.escalate();

        if (nextSeverity == null) {
            throw new AlertAlreadyCriticalException("Alert already at critical severity: " + alertId);
        }

        alert.setSeverity(nextSeverity);
        return alertRepository.save(alert);
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    private String generateAlertId() {
        return "ALT-" + String.format("%03d", alertSequence.getAndIncrement());
    }

    public static class AlertAlreadyAcknowledgedException extends RuntimeException {
        public AlertAlreadyAcknowledgedException(String message) {
            super(message);
        }
    }

    public static class AlertAlreadyResolvedException extends RuntimeException {
        public AlertAlreadyResolvedException(String message) {
            super(message);
        }
    }

    public static class AlertAlreadyCriticalException extends RuntimeException {
        public AlertAlreadyCriticalException(String message) {
            super(message);
        }
    }

    public static class AlertResolvedException extends RuntimeException {
        public AlertResolvedException(String message) {
            super(message);
        }
    }
}
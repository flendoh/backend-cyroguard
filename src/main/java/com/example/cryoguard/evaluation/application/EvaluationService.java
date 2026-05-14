package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.CreateAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.domain.entities.MonitoringRule;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertType;
import com.example.cryoguard.evaluation.infrastructure.persistence.MonitoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final MonitoringRuleRepository monitoringRuleRepository;
    private final AlertCommandService alertCommandService;

    public List<Alert> evaluateTelemetry(Long containerId, TelemetryReading reading) {
        List<Alert> alerts = new ArrayList<>();

        Optional<MonitoringRule> ruleOpt = monitoringRuleRepository.findByContainerIdAndActiveTrue(containerId);
        if (ruleOpt.isEmpty()) {
            return alerts;
        }

        MonitoringRule rule = ruleOpt.get();

        if (reading.getTemperature() != null) {
            checkTemperatureRisk(containerId, reading.getTemperature(), reading.getHumidity(), rule)
                    .ifPresent(alerts::add);
        }

        if (reading.getHumidity() != null) {
            checkHumidityRisk(containerId, reading.getHumidity(), rule)
                    .ifPresent(alerts::add);
        }

        if (reading.getVibration() != null) {
            checkVibrationRisk(containerId, reading.getVibration(), rule)
                    .ifPresent(alerts::add);
        }

        if (reading.getDoorOpen() != null && reading.getDoorOpenDurationMinutes() != null) {
            checkDoorStatus(containerId, reading.getDoorOpen(), reading.getDoorOpenDurationMinutes(), rule)
                    .ifPresent(alerts::add);
        }

        return alerts;
    }

    public Optional<Alert> checkTemperatureRisk(Long containerId, BigDecimal temperature, BigDecimal humidity, MonitoringRule rule) {
        BigDecimal minTemp = rule.getTemperatureMin();
        BigDecimal maxTemp = rule.getTemperatureMax();
        BigDecimal warningOffset = rule.getTemperatureWarningOffset();

        if (temperature.compareTo(maxTemp) > 0 || temperature.compareTo(minTemp) < 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.CRITICAL,
                    AlertType.TEMPERATURE_EXCEEDED,
                    String.format("Temperature %s°C exceeded limits [%s°C - %s°C]",
                            temperature, minTemp, maxTemp),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        if (warningOffset != null) {
            BigDecimal warningMax = maxTemp.subtract(warningOffset);
            BigDecimal warningMin = minTemp.add(warningOffset);
            if (temperature.compareTo(warningMax) > 0 || temperature.compareTo(warningMin) < 0) {
                CreateAlertCommand command = new CreateAlertCommand(
                        containerId,
                        AlertSeverity.WARNING,
                        AlertType.TEMPERATURE_EXCEEDED,
                        String.format("Temperature %s°C approaching limit [%s°C - %s°C]",
                                temperature, minTemp, maxTemp),
                        null,
                        null
                );
                return Optional.of(alertCommandService.createAlert(command));
            }
        }

        return Optional.empty();
    }

    public Optional<Alert> checkVibrationRisk(Long containerId, BigDecimal vibration, MonitoringRule rule) {
        BigDecimal criticalVibration = rule.getCriticalVibration();
        BigDecimal maxVibration = rule.getMaxVibration();

        if (criticalVibration != null && vibration.compareTo(criticalVibration) > 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.CRITICAL,
                    AlertType.VIBRATION_DETECTED,
                    String.format("Critical vibration detected: %s (max: %s)",
                            vibration, criticalVibration),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        if (maxVibration != null && vibration.compareTo(maxVibration) > 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.WARNING,
                    AlertType.VIBRATION_DETECTED,
                    String.format("High vibration detected: %s (max: %s)",
                            vibration, maxVibration),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public Optional<Alert> checkDoorStatus(Long containerId, boolean doorOpen, int durationMinutes, MonitoringRule rule) {
        Integer maxOpenDuration = rule.getMaxDoorOpenMinutes();

        if (doorOpen && durationMinutes > maxOpenDuration) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.CRITICAL,
                    AlertType.DOOR_OPENED,
                    String.format("Door open for %d minutes (max: %d)",
                            durationMinutes, maxOpenDuration),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        if (doorOpen && durationMinutes > 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.INFO,
                    AlertType.DOOR_OPENED,
                    String.format("Door opened for %d minutes", durationMinutes),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public Optional<Alert> checkHumidityRisk(Long containerId, BigDecimal humidity, MonitoringRule rule) {
        BigDecimal minHumidity = rule.getHumidityMin();
        BigDecimal maxHumidity = rule.getHumidityMax();
        BigDecimal warningOffset = rule.getHumidityWarningOffset();

        if (humidity.compareTo(maxHumidity) > 0 || humidity.compareTo(minHumidity) < 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.WARNING,
                    AlertType.HUMIDITY_ALERT,
                    String.format("Humidity %s%% outside limits [%s%% - %s%%]",
                            humidity, minHumidity, maxHumidity),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public static class TelemetryReading {
        private Long containerId;
        private BigDecimal temperature;
        private BigDecimal humidity;
        private BigDecimal vibration;
        private Boolean doorOpen;
        private Integer doorOpenDurationMinutes;

        public TelemetryReading() {}

        public Long getContainerId() { return containerId; }
        public void setContainerId(Long containerId) { this.containerId = containerId; }
        public BigDecimal getTemperature() { return temperature; }
        public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }
        public BigDecimal getHumidity() { return humidity; }
        public void setHumidity(BigDecimal humidity) { this.humidity = humidity; }
        public BigDecimal getVibration() { return vibration; }
        public void setVibration(BigDecimal vibration) { this.vibration = vibration; }
        public Boolean getDoorOpen() { return doorOpen; }
        public void setDoorOpen(Boolean doorOpen) { this.doorOpen = doorOpen; }
        public Integer getDoorOpenDurationMinutes() { return doorOpenDurationMinutes; }
        public void setDoorOpenDurationMinutes(Integer doorOpenDurationMinutes) { this.doorOpenDurationMinutes = doorOpenDurationMinutes; }
    }
}
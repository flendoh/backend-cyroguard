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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final MonitoringRuleRepository monitoringRuleRepository;
    private final AlertCommandService alertCommandService;

    public List<Alert> evaluateTelemetry(Long containerId, TelemetryReading reading) {
        List<Alert> alerts = new ArrayList<>();

        List<MonitoringRule> rules = monitoringRuleRepository.findByContainerIdAndActiveTrue(containerId);
        if (rules.isEmpty()) {
            return alerts;
        }

        Map<String, BigDecimal> paramMap = buildParameterMap(rules);

        if (reading.getTemperature() != null) {
            checkTemperatureRisk(containerId, reading.getTemperature(), reading.getHumidity(), paramMap)
                    .ifPresent(alerts::add);
        }

        if (reading.getHumidity() != null) {
            checkHumidityRisk(containerId, reading.getHumidity(), paramMap)
                    .ifPresent(alerts::add);
        }

        if (reading.getVibration() != null) {
            checkVibrationRisk(containerId, reading.getVibration(), paramMap)
                    .ifPresent(alerts::add);
        }

        if (reading.getDoorOpen() != null && reading.getDoorOpenDurationMinutes() != null) {
            checkDoorStatus(containerId, reading.getDoorOpen(), reading.getDoorOpenDurationMinutes(), paramMap)
                    .ifPresent(alerts::add);
        }

        return alerts;
    }

    public Optional<Alert> checkTemperatureRisk(Long containerId, BigDecimal temperature, BigDecimal humidity, Map<String, BigDecimal> paramMap) {
        BigDecimal minTemp = paramMap.get("temperature_min");
        BigDecimal maxTemp = paramMap.get("temperature_max");

        if (minTemp == null || maxTemp == null) {
            return Optional.empty();
        }

        if (temperature.compareTo(maxTemp) > 0 || temperature.compareTo(minTemp) < 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.CRITICAL,
                    AlertType.TEMPERATURE,
                    String.format("Temperature %s°C exceeded limits [%s°C - %s°C]",
                            temperature, minTemp, maxTemp),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public Optional<Alert> checkVibrationRisk(Long containerId, BigDecimal vibration, Map<String, BigDecimal> paramMap) {
        BigDecimal maxVibration = paramMap.get("vibration_threshold");

        if (maxVibration != null && vibration.compareTo(maxVibration) > 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.WARNING,
                    AlertType.VIBRATION,
                    String.format("High vibration detected: %s (max: %s)",
                            vibration, maxVibration),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public Optional<Alert> checkDoorStatus(Long containerId, boolean doorOpen, int durationMinutes, Map<String, BigDecimal> paramMap) {
        if (doorOpen && durationMinutes > 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.INFO,
                    AlertType.DOOR,
                    String.format("Door opened for %d minutes", durationMinutes),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    public Optional<Alert> checkHumidityRisk(Long containerId, BigDecimal humidity, Map<String, BigDecimal> paramMap) {
        BigDecimal minHumidity = paramMap.get("humidity_min");
        BigDecimal maxHumidity = paramMap.get("humidity_max");

        if (minHumidity == null || maxHumidity == null) {
            return Optional.empty();
        }

        if (humidity.compareTo(maxHumidity) > 0 || humidity.compareTo(minHumidity) < 0) {
            CreateAlertCommand command = new CreateAlertCommand(
                    containerId,
                    AlertSeverity.WARNING,
                    AlertType.HUMIDITY,
                    String.format("Humidity %s%% outside limits [%s%% - %s%%]",
                            humidity, minHumidity, maxHumidity),
                    null,
                    null
            );
            return Optional.of(alertCommandService.createAlert(command));
        }

        return Optional.empty();
    }

    private Map<String, BigDecimal> buildParameterMap(List<MonitoringRule> rules) {
        return rules.stream()
                .collect(Collectors.toMap(
                        MonitoringRule::getParameter,
                        MonitoringRule::getThresholdValue,
                        (v1, v2) -> v1
                ));
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
package com.example.cryoguard.evaluation.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMonitoringRuleCommand {
    private Long containerId;
    private BigDecimal temperatureMin;
    private BigDecimal temperatureMax;
    private BigDecimal temperatureWarningOffset;
    private BigDecimal humidityMin;
    private BigDecimal humidityMax;
    private BigDecimal humidityWarningOffset;
    private BigDecimal maxVibration;
    private BigDecimal criticalVibration;
    private Integer maxDoorOpenMinutes;
    private Boolean active;
}
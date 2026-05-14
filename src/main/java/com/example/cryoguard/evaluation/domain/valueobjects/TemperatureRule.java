package com.example.cryoguard.evaluation.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureRule {
    private BigDecimal minTemp;
    private BigDecimal maxTemp;
    private BigDecimal warningThreshold;
    private BigDecimal criticalThreshold;
}
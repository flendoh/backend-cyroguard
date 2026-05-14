package com.example.cryoguard.evaluation.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumidityRule {
    private BigDecimal minHumidity;
    private BigDecimal maxHumidity;
    private BigDecimal warningThreshold;
    private BigDecimal criticalThreshold;
}
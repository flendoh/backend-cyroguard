package com.example.cryoguard.evaluation.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VibrationRule {
    private BigDecimal maxVibration;
    private BigDecimal criticalVibration;
}
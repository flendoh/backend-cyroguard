package com.example.cryoguard.evaluation.presentation.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringRuleResource {
    private Long id;
    private String name;
    private String parameter;
    private BigDecimal value;
    private String unit;
    private String updatedAt;
}
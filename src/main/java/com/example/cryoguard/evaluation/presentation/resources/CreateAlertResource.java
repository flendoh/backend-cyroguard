package com.example.cryoguard.evaluation.presentation.resources;

import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlertResource {
    private Long containerId;
    private AlertSeverity severity;
    private AlertType alertType;
    private String message;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
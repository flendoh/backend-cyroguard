package com.example.cryoguard.evaluation.presentation.resources;

import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import com.example.cryoguard.evaluation.domain.valueobjects.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertResource {
    private Long id;
    private String alertId;
    private Long containerId;
    private AlertSeverity severity;
    private AlertType alertType;
    private String message;
    private LocalDateTime timestamp;
    private AlertStatus status;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long acknowledgedBy;
    private LocalDateTime acknowledgedAt;
    private Long resolvedBy;
    private LocalDateTime resolvedAt;
}
package com.example.cryoguard.evaluation.presentation.resources;

import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
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
    private AlertType alertType;
    private AlertSeverity severity;
    private String message;
    private LocalDateTime timestamp;
    private Boolean acknowledged;
    private Long acknowledgedBy;
    private LocalDateTime acknowledgedAt;
    private Boolean resolved;
    private Long resolvedBy;
    private LocalDateTime resolvedAt;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
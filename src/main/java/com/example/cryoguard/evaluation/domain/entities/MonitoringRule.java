package com.example.cryoguard.evaluation.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoring_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", nullable = false)
    private Long containerId;

    @Column(name = "parameter", nullable = false)
    private String parameter;

    @Column(name = "threshold_value", precision = 10, scale = 2)
    private BigDecimal thresholdValue;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
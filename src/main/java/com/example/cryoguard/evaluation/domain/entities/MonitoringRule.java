package com.example.cryoguard.evaluation.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @Column(name = "temperature_min", precision = 10, scale = 2)
    private BigDecimal temperatureMin;

    @Column(name = "temperature_max", precision = 10, scale = 2)
    private BigDecimal temperatureMax;

    @Column(name = "temperature_warning_offset", precision = 10, scale = 2)
    private BigDecimal temperatureWarningOffset;

    @Column(name = "humidity_min", precision = 10, scale = 2)
    private BigDecimal humidityMin;

    @Column(name = "humidity_max", precision = 10, scale = 2)
    private BigDecimal humidityMax;

    @Column(name = "humidity_warning_offset", precision = 10, scale = 2)
    private BigDecimal humidityWarningOffset;

    @Column(name = "max_vibration", precision = 10, scale = 2)
    private BigDecimal maxVibration;

    @Column(name = "max_door_open_minutes")
    private Integer maxDoorOpenMinutes;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "critical_vibration", precision = 10, scale = 2)
    private BigDecimal criticalVibration;
}
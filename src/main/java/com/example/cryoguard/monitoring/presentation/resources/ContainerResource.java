package com.example.cryoguard.monitoring.presentation.resources;

import com.example.cryoguard.monitoring.domain.valueobjects.ConnectivityStatus;
import com.example.cryoguard.monitoring.domain.valueobjects.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResource {
    private Long id;
    private String containerId;
    private String name;
    private String deviceId;
    private ContainerStatus status;
    private BigDecimal currentTemperature;
    private BigDecimal currentHumidity;
    private BigDecimal currentVibration;
    private Integer batteryLevel;
    private ConnectivityStatus connectivity;
    private BigDecimal gpsLatitude;
    private BigDecimal gpsLongitude;
    private LocalDateTime lastSync;
    private Long assignedOperatorId;
    private BigDecimal temperatureMin;
    private BigDecimal temperatureMax;
    private BigDecimal humidityMin;
    private BigDecimal humidityMax;
}
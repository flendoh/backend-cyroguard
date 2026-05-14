package com.example.cryoguard.monitoring.presentation.resources;

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
    private String status;  // lowercase: active|inactive|maintenance
    private GpsLocationDTO currentLocation;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private Integer batteryLevel;
    private LocalDateTime lastUpdate;
    private String productType;
    private String deviceId;
    private Long operatorId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GpsLocationDTO {
        private BigDecimal lat;
        private BigDecimal lng;
    }
}
package com.example.cryoguard.monitoring.domain.aggregates;

import com.example.cryoguard.monitoring.domain.valueobjects.ConnectivityStatus;
import com.example.cryoguard.monitoring.domain.valueobjects.ContainerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "containers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id", unique = true, nullable = false)
    private String containerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "device_id")
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerStatus status = ContainerStatus.NORMAL;

    @Column(name = "current_temperature", precision = 5, scale = 2)
    private BigDecimal currentTemperature;

    @Column(name = "current_humidity", precision = 5, scale = 2)
    private BigDecimal currentHumidity;

    @Column(name = "current_vibration", precision = 5, scale = 2)
    private BigDecimal currentVibration;

    @Column(name = "battery_level")
    private Integer batteryLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectivityStatus connectivity = ConnectivityStatus.OFFLINE;

    @Column(name = "gps_latitude", precision = 10, scale = 6)
    private BigDecimal gpsLatitude;

    @Column(name = "gps_longitude", precision = 10, scale = 6)
    private BigDecimal gpsLongitude;

    @Column(name = "last_sync")
    private LocalDateTime lastSync;

    @Column(name = "assigned_operator_id")
    private Long assignedOperatorId;

    @Column(name = "temperature_min", precision = 5, scale = 2)
    private BigDecimal temperatureMin;

    @Column(name = "temperature_max", precision = 5, scale = 2)
    private BigDecimal temperatureMax;

    @Column(name = "humidity_min", precision = 5, scale = 2)
    private BigDecimal humidityMin;

    @Column(name = "humidity_max", precision = 5, scale = 2)
    private BigDecimal humidityMax;

    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (lastSync != null && lastSync.plusMinutes(30).isBefore(now)) {
            this.status = ContainerStatus.OFFLINE;
            return;
        }

        if (currentTemperature == null) {
            return;
        }

        if (temperatureMax != null && currentTemperature.compareTo(temperatureMax) > 0) {
            this.status = ContainerStatus.CRITICAL;
        } else if (temperatureMin != null && currentTemperature.compareTo(temperatureMin) < 0) {
            this.status = ContainerStatus.CRITICAL;
        } else if (temperatureMax != null && currentTemperature.compareTo(temperatureMax.subtract(BigDecimal.ONE)) >= 0) {
            this.status = ContainerStatus.PREVENTIVO;
        } else if (temperatureMin != null && currentTemperature.compareTo(temperatureMin.add(BigDecimal.ONE)) <= 0) {
            this.status = ContainerStatus.PREVENTIVO;
        } else {
            this.status = ContainerStatus.NORMAL;
        }
    }
}
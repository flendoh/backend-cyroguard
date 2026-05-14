package com.example.cryoguard.monitoring.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncContainerCommand {
    private Long containerId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime timestamp;

    public SyncContainerCommand(Long containerId, BigDecimal latitude, BigDecimal longitude) {
        this.containerId = containerId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
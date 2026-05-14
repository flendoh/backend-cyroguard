package com.example.cryoguard.monitoring.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncContainerCommand {
    private Long containerId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
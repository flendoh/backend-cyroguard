package com.example.cryoguard.monitoring.presentation.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResource {
    private Long id;
    private Long containerId;
    private String type;
    private String severity;
    private String message;
    private LocalDateTime timestamp;
    private Boolean acknowledged;
    private Long acknowledgedBy;
    private LocalDateTime acknowledgedAt;
}
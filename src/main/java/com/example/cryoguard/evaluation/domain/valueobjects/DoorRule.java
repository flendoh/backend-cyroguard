package com.example.cryoguard.evaluation.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoorRule {
    private Integer maxOpenDurationMinutes;
}
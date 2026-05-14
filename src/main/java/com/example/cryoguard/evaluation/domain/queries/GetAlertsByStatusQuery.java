package com.example.cryoguard.evaluation.domain.queries;

import com.example.cryoguard.evaluation.domain.valueobjects.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAlertsByStatusQuery {
    private AlertStatus status;
}
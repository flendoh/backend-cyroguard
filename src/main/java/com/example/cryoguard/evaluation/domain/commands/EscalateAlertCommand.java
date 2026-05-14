package com.example.cryoguard.evaluation.domain.commands;

import com.example.cryoguard.evaluation.domain.valueobjects.AlertSeverity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalateAlertCommand {
    private Long alertId;
    private AlertSeverity newSeverity;
}
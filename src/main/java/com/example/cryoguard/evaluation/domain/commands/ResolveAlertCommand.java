package com.example.cryoguard.evaluation.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolveAlertCommand {
    private Long alertId;
    private Long userId;
}
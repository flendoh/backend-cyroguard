package com.example.cryoguard.evaluation.application;

import com.example.cryoguard.evaluation.domain.commands.AcknowledgeAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.CreateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.EscalateAlertCommand;
import com.example.cryoguard.evaluation.domain.commands.ResolveAlertCommand;
import com.example.cryoguard.evaluation.domain.entities.Alert;

import java.util.List;

public interface AlertCommandService {

    Alert createAlert(CreateAlertCommand command);

    Alert acknowledgeAlert(AcknowledgeAlertCommand command);

    Alert resolveAlert(ResolveAlertCommand command);

    Alert escalateAlert(Long alertId);

    List<Alert> getAllAlerts();
}
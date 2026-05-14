package com.example.cryoguard.evaluation.presentation.assemblers;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import com.example.cryoguard.evaluation.presentation.resources.AlertResource;
import org.springframework.stereotype.Component;

@Component
public class AlertAssembler {

    public AlertResource toResource(Alert alert) {
        AlertResource resource = new AlertResource();
        resource.setId(alert.getId());
        resource.setAlertId(alert.getAlertId());
        resource.setContainerId(alert.getContainerId());
        resource.setSeverity(alert.getSeverity());
        resource.setAlertType(alert.getAlertType());
        resource.setMessage(alert.getMessage());
        resource.setTimestamp(alert.getTimestamp());
        resource.setStatus(alert.getStatus());
        resource.setLatitude(alert.getLatitude());
        resource.setLongitude(alert.getLongitude());
        resource.setAcknowledgedBy(alert.getAcknowledgedBy());
        resource.setAcknowledgedAt(alert.getAcknowledgedAt());
        resource.setResolvedBy(alert.getResolvedBy());
        resource.setResolvedAt(alert.getResolvedAt());
        return resource;
    }
}
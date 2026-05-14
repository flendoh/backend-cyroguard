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
        resource.setAlertType(alert.getAlertType());
        resource.setSeverity(alert.getSeverity());
        resource.setMessage(alert.getMessage());
        resource.setTimestamp(alert.getTimestamp());
        resource.setAcknowledged(alert.getAcknowledged());
        resource.setAcknowledgedBy(alert.getAcknowledgedBy());
        resource.setAcknowledgedAt(alert.getAcknowledgedAt());
        resource.setResolved(alert.getResolved());
        resource.setResolvedBy(alert.getResolvedBy());
        resource.setResolvedAt(alert.getResolvedAt());
        resource.setLatitude(alert.getLatitude());
        resource.setLongitude(alert.getLongitude());
        return resource;
    }
}
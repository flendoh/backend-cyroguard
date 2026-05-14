package com.example.cryoguard.monitoring.presentation.assemblers;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.presentation.resources.ContainerResource;
import com.example.cryoguard.monitoring.presentation.resources.CreateContainerResource;
import org.springframework.stereotype.Component;

@Component
public class ContainerResourceAssembler {

    public ContainerResource toResource(Container container) {
        return new ContainerResource(
                container.getId(),
                container.getContainerId(),
                container.getName(),
                container.getDeviceId(),
                container.getStatus(),
                container.getCurrentTemperature(),
                container.getCurrentHumidity(),
                container.getCurrentVibration(),
                container.getBatteryLevel(),
                container.getConnectivity(),
                container.getGpsLatitude(),
                container.getGpsLongitude(),
                container.getLastSync(),
                container.getAssignedOperatorId(),
                container.getTemperatureMin(),
                container.getTemperatureMax(),
                container.getHumidityMin(),
                container.getHumidityMax()
        );
    }

    public Container toEntity(CreateContainerResource resource) {
        Container container = new Container();
        container.setContainerId(resource.getContainerId());
        container.setName(resource.getName());
        container.setDeviceId(resource.getDeviceId());
        container.setTemperatureMin(resource.getTemperatureMin());
        container.setTemperatureMax(resource.getTemperatureMax());
        container.setHumidityMin(resource.getHumidityMin());
        container.setHumidityMax(resource.getHumidityMax());
        return container;
    }
}
package com.example.cryoguard.monitoring.presentation.assemblers;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.domain.valueobjects.GpsCoordinates;
import com.example.cryoguard.monitoring.presentation.resources.ContainerResource;
import com.example.cryoguard.monitoring.presentation.resources.CreateContainerResource;
import org.springframework.stereotype.Component;

@Component
public class ContainerResourceAssembler {

    public ContainerResource toResource(Container container) {
        ContainerResource.GpsLocationDTO locationDTO = null;
        if (container.getCurrentLocation() != null) {
            GpsCoordinates loc = container.getCurrentLocation();
            locationDTO = new ContainerResource.GpsLocationDTO(loc.getLatitude(), loc.getLongitude());
        }

        return new ContainerResource(
                container.getId(),
                container.getContainerId(),
                container.getName(),
                container.getStatus().name().toLowerCase(),
                locationDTO,
                container.getCurrentTemperature(),
                container.getCurrentHumidity(),
                container.getBatteryLevel(),
                container.getLastUpdate(),
                container.getProductType(),
                container.getDeviceId(),
                container.getOperatorId()
        );
    }

    public Container toEntity(CreateContainerResource resource) {
        Container container = new Container();
        container.setContainerId(resource.getContainerId());
        container.setName(resource.getName());
        container.setDeviceId(resource.getDeviceId());
        return container;
    }
}
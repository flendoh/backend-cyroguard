package com.example.cryoguard.logistics.presentation.assemblers;

import com.example.cryoguard.logistics.domain.commands.CreateGeofenceCommand;
import com.example.cryoguard.logistics.domain.entities.Geofence;
import com.example.cryoguard.logistics.presentation.resources.CreateGeofenceResource;
import com.example.cryoguard.logistics.presentation.resources.GeofenceResource;

import java.util.List;

public class GeofenceAssembler {

    public static GeofenceResource toResource(Geofence geofence) {
        return new GeofenceResource(
            geofence.getId(),
            geofence.getGeofenceId(),
            geofence.getName(),
            geofence.getType(),
            geofence.getStatus(),
            geofence.getCenterLatitude(),
            geofence.getCenterLongitude(),
            geofence.getRadiusMeters(),
            geofence.getCreatedAt()
        );
    }

    public static CreateGeofenceCommand toCreateCommand(CreateGeofenceResource resource) {
        return new CreateGeofenceCommand(
            resource.name(),
            resource.type(),
            resource.centerLatitude(),
            resource.centerLongitude(),
            resource.radiusMeters()
        );
    }

    public static List<GeofenceResource> toResourceList(List<Geofence> geofences) {
        return geofences.stream().map(GeofenceAssembler::toResource).toList();
    }
}
package com.example.cryoguard.logistics.presentation.assemblers;

import com.example.cryoguard.logistics.domain.commands.CreateGeofenceCommand;
import com.example.cryoguard.logistics.domain.entities.Geofence;
import com.example.cryoguard.logistics.domain.valueobjects.Coordinate;
import com.example.cryoguard.logistics.presentation.resources.CreateGeofenceResource;
import com.example.cryoguard.logistics.presentation.resources.GeofenceResource;

import java.util.List;

public class GeofenceAssembler {

    public static GeofenceResource toResource(Geofence geofence) {
        List<GeofenceResource.CoordinateResource> coordinates = geofence.getCoordinates().stream()
            .map(c -> new GeofenceResource.CoordinateResource(c.getLatitude(), c.getLongitude()))
            .toList();

        return new GeofenceResource(
            geofence.getId(),
            geofence.getGeofenceId(),
            geofence.getName(),
            geofence.getType(),
            geofence.getStatus(),
            coordinates,
            geofence.getRadiusMeters(),
            geofence.getCreatedAt()
        );
    }

    public static CreateGeofenceCommand toCreateCommand(CreateGeofenceResource resource) {
        List<CreateGeofenceCommand.CoordinateCommand> coordinates = resource.coordinates().stream()
            .map(c -> new CreateGeofenceCommand.CoordinateCommand(c.lat(), c.lng()))
            .toList();

        return new CreateGeofenceCommand(
            resource.name(),
            resource.type(),
            coordinates,
            resource.radiusMeters()
        );
    }

    public static List<GeofenceResource> toResourceList(List<Geofence> geofences) {
        return geofences.stream().map(GeofenceAssembler::toResource).toList();
    }
}
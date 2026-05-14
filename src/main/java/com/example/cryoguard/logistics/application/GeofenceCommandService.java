package com.example.cryoguard.logistics.application;

import com.example.cryoguard.logistics.domain.commands.CreateGeofenceCommand;
import com.example.cryoguard.logistics.domain.entities.Geofence;
import com.example.cryoguard.logistics.domain.valueobjects.Coordinate;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.infrastructure.persistence.GeofenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GeofenceCommandService {

    private final GeofenceRepository geofenceRepository;

    public GeofenceCommandService(GeofenceRepository geofenceRepository) {
        this.geofenceRepository = geofenceRepository;
    }

    public Geofence createGeofence(CreateGeofenceCommand command) {
        String geofenceId = generateGeofenceId();
        List<Coordinate> coordinates = command.coordinates().stream()
            .map(c -> new Coordinate(c.latitude(), c.longitude()))
            .toList();
        Geofence geofence = new Geofence(
            geofenceId,
            command.name(),
            command.type(),
            GeofenceStatus.active,
            coordinates,
            command.radiusMeters(),
            java.time.LocalDateTime.now()
        );
        return geofenceRepository.save(geofence);
    }

    public Geofence updateGeofence(Long id, CreateGeofenceCommand command) {
        Geofence geofence = geofenceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Geofence not found: " + id));
        geofence.setName(command.name());
        geofence.setType(command.type());
        List<Coordinate> coordinates = command.coordinates().stream()
            .map(c -> new Coordinate(c.latitude(), c.longitude()))
            .toList();
        geofence.setCoordinates(coordinates);
        geofence.setRadiusMeters(command.radiusMeters());
        return geofenceRepository.save(geofence);
    }

    public void deleteGeofence(Long id) {
        Geofence geofence = geofenceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Geofence not found: " + id));
        geofenceRepository.delete(geofence);
    }

    private String generateGeofenceId() {
        long count = geofenceRepository.count();
        return String.format("GEO-%03d", count + 1);
    }
}
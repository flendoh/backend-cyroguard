package com.example.cryoguard.logistics.application;

import com.example.cryoguard.logistics.domain.entities.Geofence;
import com.example.cryoguard.logistics.domain.queries.GetAllGeofencesQuery;
import com.example.cryoguard.logistics.domain.queries.GetGeofenceByIdQuery;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.infrastructure.persistence.GeofenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GeofenceQueryService {

    private final GeofenceRepository geofenceRepository;

    public GeofenceQueryService(GeofenceRepository geofenceRepository) {
        this.geofenceRepository = geofenceRepository;
    }

    public Geofence getById(GetGeofenceByIdQuery query) {
        return geofenceRepository.findById(query.id())
            .orElseThrow(() -> new IllegalArgumentException("Geofence not found: " + query.id()));
    }

    public List<Geofence> getAllGeofences(GetAllGeofencesQuery query) {
        return geofenceRepository.findAll();
    }

    public List<Geofence> getActiveGeofences() {
        return geofenceRepository.findByStatus(GeofenceStatus.active);
    }
}
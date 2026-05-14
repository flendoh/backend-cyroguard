package com.example.cryoguard.logistics.infrastructure.persistence;

import com.example.cryoguard.logistics.domain.entities.Geofence;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
    Optional<Geofence> findByGeofenceId(String geofenceId);
    List<Geofence> findByStatus(GeofenceStatus status);
}
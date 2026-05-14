package com.example.cryoguard.logistics.application;

import com.example.cryoguard.logistics.domain.aggregates.Route;
import com.example.cryoguard.logistics.domain.commands.CompleteRouteCommand;
import com.example.cryoguard.logistics.domain.commands.CreateRouteCommand;
import com.example.cryoguard.logistics.domain.commands.RecordRouteLocationCommand;
import com.example.cryoguard.logistics.domain.commands.UpdateRouteCommand;
import com.example.cryoguard.logistics.domain.entities.RouteLocationHistory;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceStatus;
import com.example.cryoguard.logistics.domain.valueobjects.GeofenceType;
import com.example.cryoguard.logistics.domain.valueobjects.RouteStatus;
import com.example.cryoguard.logistics.infrastructure.persistence.GeofenceRepository;
import com.example.cryoguard.logistics.infrastructure.persistence.RouteLocationHistoryRepository;
import com.example.cryoguard.logistics.infrastructure.persistence.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RouteCommandService {

    private final RouteRepository routeRepository;
    private final GeofenceRepository geofenceRepository;
    private final RouteLocationHistoryRepository locationHistoryRepository;

    public RouteCommandService(RouteRepository routeRepository, GeofenceRepository geofenceRepository,
                               RouteLocationHistoryRepository locationHistoryRepository) {
        this.routeRepository = routeRepository;
        this.geofenceRepository = geofenceRepository;
        this.locationHistoryRepository = locationHistoryRepository;
    }

    public Route createRoute(CreateRouteCommand command) {
        String routeId = generateRouteId();
        Route route = new Route(
            routeId,
            command.name(),
            command.containerId(),
            RouteStatus.ACTIVE,
            command.startLocation(),
            command.endLocation(),
            command.distanceKm(),
            command.estimatedDurationMinutes(),
            command.checkpoints(),
            command.startTime()
        );
        return routeRepository.save(route);
    }

    public Route updateRoute(Long id, UpdateRouteCommand command) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Route not found: " + id));
        route.setName(command.name());
        route.setStartLocation(command.startLocation());
        route.setEndLocation(command.endLocation());
        route.setDistanceKm(command.distanceKm());
        route.setEstimatedDurationMinutes(command.estimatedDurationMinutes());
        route.setCheckpoints(command.checkpoints());
        route.setStartTime(command.startTime());
        return routeRepository.save(route);
    }

    public Route completeRoute(Long id, CompleteRouteCommand command) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Route not found: " + id));
        if (route.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalStateException("Only active routes can be completed");
        }
        route.complete();
        return routeRepository.save(route);
    }

    public RouteLocationHistory recordLocation(Long routeId, RecordRouteLocationCommand command) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new IllegalArgumentException("Route not found: " + routeId));

        RouteLocationHistory location = new RouteLocationHistory(
            route,
            command.timestamp(),
            command.latitude(),
            command.longitude(),
            command.speed(),
            command.heading()
        );
        RouteLocationHistory saved = locationHistoryRepository.save(location);

        checkGeofenceViolation(command.latitude(), command.longitude());

        return saved;
    }

    private void checkGeofenceViolation(BigDecimal latitude, BigDecimal longitude) {
        Set<String> activeAuthorizedZones = new HashSet<>();
        Set<String> activeRestrictedZones = new HashSet<>();

        geofenceRepository.findByStatus(GeofenceStatus.ACTIVE).forEach(geofence -> {
            boolean isInside = geofence.containsPoint(latitude, longitude);
            if (geofence.getType() == GeofenceType.AUTHORIZED_ZONE) {
                if (!isInside) {
                    activeAuthorizedZones.add(geofence.getName());
                }
            } else if (geofence.getType() == GeofenceType.RESTRICTED_ZONE) {
                if (isInside) {
                    activeRestrictedZones.add(geofence.getName());
                }
            }
        });

        if (!activeRestrictedZones.isEmpty()) {
            throw new IllegalStateException("GEOFENCE_VIOLATION: Container entered restricted zone(s): " + activeRestrictedZones);
        }
        if (!activeAuthorizedZones.isEmpty()) {
            throw new IllegalStateException("GEOFENCE_VIOLATION: Container exited authorized zone(s): " + activeAuthorizedZones);
        }
    }

    private String generateRouteId() {
        long count = routeRepository.count();
        return String.format("ROUTE-%03d", count + 1);
    }
}
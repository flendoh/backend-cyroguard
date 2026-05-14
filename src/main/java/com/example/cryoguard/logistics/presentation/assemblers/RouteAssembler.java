package com.example.cryoguard.logistics.presentation.assemblers;

import com.example.cryoguard.logistics.domain.aggregates.Route;
import com.example.cryoguard.logistics.domain.commands.CreateRouteCommand;
import com.example.cryoguard.logistics.domain.commands.UpdateRouteCommand;
import com.example.cryoguard.logistics.domain.entities.RouteCheckpoint;
import com.example.cryoguard.logistics.domain.entities.RouteLocationHistory;
import com.example.cryoguard.logistics.presentation.resources.CreateRouteResource;
import com.example.cryoguard.logistics.presentation.resources.RouteLocationResource;
import com.example.cryoguard.logistics.presentation.resources.RouteResource;
import com.example.cryoguard.logistics.presentation.resources.UpdateRouteResource;

import java.util.List;

public class RouteAssembler {

    public static RouteResource toResource(Route route) {
        return new RouteResource(
            route.getId(),
            route.getRouteId(),
            route.getName(),
            route.getContainerId(),
            route.getStatus(),
            route.getStartLocation(),
            route.getEndLocation(),
            route.getDistanceKm(),
            route.getEstimatedDurationMinutes(),
            route.getCheckpoints(),
            route.getStartTime(),
            route.getEndTime()
        );
    }

    public static CreateRouteCommand toCreateCommand(CreateRouteResource resource) {
        return new CreateRouteCommand(
            resource.name(),
            resource.containerId(),
            resource.startLocation(),
            resource.endLocation(),
            resource.distanceKm(),
            resource.estimatedDurationMinutes(),
            resource.checkpoints(),
            resource.startTime()
        );
    }

    public static UpdateRouteCommand toUpdateCommand(UpdateRouteResource resource) {
        return new UpdateRouteCommand(
            resource.name(),
            resource.startLocation(),
            resource.endLocation(),
            resource.distanceKm(),
            resource.estimatedDurationMinutes(),
            resource.checkpoints(),
            resource.startTime()
        );
    }

    public static RouteLocationResource toLocationResource(RouteLocationHistory location) {
        return new RouteLocationResource(
            location.getId(),
            location.getRoute().getId(),
            location.getTimestamp(),
            location.getLatitude(),
            location.getLongitude(),
            location.getSpeed(),
            location.getHeading()
        );
    }

    public static List<RouteResource> toResourceList(List<Route> routes) {
        return routes.stream().map(RouteAssembler::toResource).toList();
    }

    public static List<RouteLocationResource> toLocationResourceList(List<RouteLocationHistory> locations) {
        return locations.stream().map(RouteAssembler::toLocationResource).toList();
    }
}
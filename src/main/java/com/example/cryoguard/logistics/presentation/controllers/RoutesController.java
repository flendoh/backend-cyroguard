package com.example.cryoguard.logistics.presentation.controllers;

import com.example.cryoguard.logistics.application.RouteCommandService;
import com.example.cryoguard.logistics.application.RouteQueryService;
import com.example.cryoguard.logistics.domain.commands.CompleteRouteCommand;
import com.example.cryoguard.logistics.domain.commands.CreateRouteCommand;
import com.example.cryoguard.logistics.domain.commands.RecordRouteLocationCommand;
import com.example.cryoguard.logistics.domain.commands.UpdateRouteCommand;
import com.example.cryoguard.logistics.domain.entities.RouteLocationHistory;
import com.example.cryoguard.logistics.domain.queries.GetActiveRoutesQuery;
import com.example.cryoguard.logistics.domain.queries.GetRouteByIdQuery;
import com.example.cryoguard.logistics.domain.queries.GetRouteHistoryQuery;
import com.example.cryoguard.logistics.domain.queries.GetRoutesByContainerQuery;
import com.example.cryoguard.logistics.domain.valueobjects.RouteStatus;
import com.example.cryoguard.logistics.presentation.assemblers.RouteAssembler;
import com.example.cryoguard.logistics.presentation.resources.CreateRouteResource;
import com.example.cryoguard.logistics.presentation.resources.RouteLocationResource;
import com.example.cryoguard.logistics.presentation.resources.RouteResource;
import com.example.cryoguard.logistics.presentation.resources.UpdateRouteResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/routes")
@Tag(name = "Routes", description = "Route and logistics management operations")
public class RoutesController {

    private final RouteCommandService commandService;
    private final RouteQueryService queryService;

    public RoutesController(RouteCommandService commandService, RouteQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get all routes", description = "Retrieves all routes with optional filtering by container ID or status.")
    public ResponseEntity<List<RouteResource>> getRoutes(
            @RequestParam(required = false) Long containerId,
            @RequestParam(required = false) RouteStatus status) {
        List<RouteResource> routes;
        if (containerId != null) {
            routes = RouteAssembler.toResourceList(queryService.getByContainer(new GetRoutesByContainerQuery(containerId)));
        } else if (status != null) {
            routes = RouteAssembler.toResourceList(queryService.getActiveRoutes(new GetActiveRoutesQuery()));
        } else {
            routes = RouteAssembler.toResourceList(queryService.getAllRoutes());
        }
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get route by ID", description = "Retrieves a specific route by its unique identifier.")
    public ResponseEntity<RouteResource> getRoute(@PathVariable Long id) {
        RouteResource route = RouteAssembler.toResource(queryService.getById(new GetRouteByIdQuery(id)));
        return ResponseEntity.ok(route);
    }

    @PostMapping
    @Operation(summary = "Create new route", description = "Creates a new logistics route for container transportation.")
    public ResponseEntity<RouteResource> createRoute(@Valid @RequestBody CreateRouteResource resource) {
        CreateRouteCommand command = RouteAssembler.toCreateCommand(resource);
        RouteResource created = RouteAssembler.toResource(commandService.createRoute(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update route", description = "Updates an existing route with new details.")
    public ResponseEntity<RouteResource> updateRoute(@PathVariable Long id, @Valid @RequestBody UpdateRouteResource resource) {
        UpdateRouteCommand command = RouteAssembler.toUpdateCommand(resource);
        RouteResource updated = RouteAssembler.toResource(commandService.updateRoute(id, command));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete route", description = "Marks a route as completed.")
    public ResponseEntity<RouteResource> completeRoute(@PathVariable Long id) {
        RouteResource completed = RouteAssembler.toResource(commandService.completeRoute(id, new CompleteRouteCommand()));
        return ResponseEntity.ok(completed);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get route location history", description = "Retrieves the location history for a specific route.")
    public ResponseEntity<List<RouteLocationResource>> getRouteHistory(@PathVariable Long id) {
        List<RouteLocationResource> history = RouteAssembler.toLocationResourceList(queryService.getRouteHistory(new GetRouteHistoryQuery(id)));
        return ResponseEntity.ok(history);
    }

    @PostMapping("/{id}/location")
    @Operation(summary = "Record route location", description = "Records a new GPS location point for an active route.")
    public ResponseEntity<RouteLocationResource> recordLocation(@PathVariable Long id, @RequestBody RouteLocationResource resource) {
        RecordRouteLocationCommand command = new RecordRouteLocationCommand(
            resource.timestamp(),
            resource.latitude(),
            resource.longitude(),
            resource.speed(),
            resource.heading()
        );
        RouteLocationHistory recorded = commandService.recordLocation(id, command);
        return ResponseEntity.status(HttpStatus.CREATED).body(RouteAssembler.toLocationResource(recorded));
    }
}
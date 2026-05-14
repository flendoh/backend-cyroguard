package com.example.cryoguard.logistics.presentation.controllers;

import com.example.cryoguard.logistics.application.GeofenceCommandService;
import com.example.cryoguard.logistics.application.GeofenceQueryService;
import com.example.cryoguard.logistics.domain.commands.CreateGeofenceCommand;
import com.example.cryoguard.logistics.domain.queries.GetAllGeofencesQuery;
import com.example.cryoguard.logistics.domain.queries.GetGeofenceByIdQuery;
import com.example.cryoguard.logistics.presentation.assemblers.GeofenceAssembler;
import com.example.cryoguard.logistics.presentation.resources.CreateGeofenceResource;
import com.example.cryoguard.logistics.presentation.resources.GeofenceResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geofences")
@Tag(name = "Geofences", description = "Geofence and zone management operations")
public class GeofencesController {

    private final GeofenceCommandService commandService;
    private final GeofenceQueryService queryService;

    public GeofencesController(GeofenceCommandService commandService, GeofenceQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get all geofences", description = "Retrieves all geofences (virtual zones) in the system.")
    public ResponseEntity<List<GeofenceResource>> getGeofences() {
        List<GeofenceResource> geofences = GeofenceAssembler.toResourceList(queryService.getAllGeofences(new GetAllGeofencesQuery()));
        return ResponseEntity.ok(geofences);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get geofence by ID", description = "Retrieves a specific geofence by its unique identifier.")
    public ResponseEntity<GeofenceResource> getGeofence(@PathVariable Long id) {
        GeofenceResource geofence = GeofenceAssembler.toResource(queryService.getById(new GetGeofenceByIdQuery(id)));
        return ResponseEntity.ok(geofence);
    }

    @PostMapping
    @Operation(summary = "Create geofence", description = "Creates a new geofence (virtual zone) for monitoring.")
    public ResponseEntity<GeofenceResource> createGeofence(@Valid @RequestBody CreateGeofenceResource resource) {
        CreateGeofenceCommand command = GeofenceAssembler.toCreateCommand(resource);
        GeofenceResource created = GeofenceAssembler.toResource(commandService.createGeofence(command));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update geofence", description = "Updates an existing geofence with new zone parameters.")
    public ResponseEntity<GeofenceResource> updateGeofence(@PathVariable Long id, @Valid @RequestBody CreateGeofenceResource resource) {
        CreateGeofenceCommand command = GeofenceAssembler.toCreateCommand(resource);
        GeofenceResource updated = GeofenceAssembler.toResource(commandService.updateGeofence(id, command));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete geofence", description = "Deletes a geofence from the system.")
    public ResponseEntity<Void> deleteGeofence(@PathVariable Long id) {
        commandService.deleteGeofence(id);
        return ResponseEntity.noContent().build();
    }
}
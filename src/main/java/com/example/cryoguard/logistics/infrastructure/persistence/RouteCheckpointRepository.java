package com.example.cryoguard.logistics.infrastructure.persistence;

import com.example.cryoguard.logistics.domain.entities.RouteCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteCheckpointRepository extends JpaRepository<RouteCheckpoint, Long> {
    List<RouteCheckpoint> findByRouteIdOrderBySequence(Long routeId);
}
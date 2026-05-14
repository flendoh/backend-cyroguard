package com.example.cryoguard.logistics.infrastructure.persistence;

import com.example.cryoguard.logistics.domain.aggregates.Route;
import com.example.cryoguard.logistics.domain.valueobjects.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByRouteId(String routeId);
    List<Route> findByContainerId(Long containerId);
    List<Route> findByStatus(RouteStatus status);
    List<Route> findByContainerIdAndStatus(Long containerId, RouteStatus status);
}
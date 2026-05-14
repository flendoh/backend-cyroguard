package com.example.cryoguard.logistics.infrastructure.persistence;

import com.example.cryoguard.logistics.domain.entities.RouteLocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteLocationHistoryRepository extends JpaRepository<RouteLocationHistory, Long> {
    List<RouteLocationHistory> findByRouteIdOrderByTimestamp(Long routeId);
}
package com.example.cryoguard.monitoring.infrastructure.persistence;

import com.example.cryoguard.monitoring.domain.entities.Event;
import com.example.cryoguard.monitoring.domain.entities.EventSeverity;
import com.example.cryoguard.monitoring.domain.entities.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByContainerIdOrderByTimestampDesc(Long containerId, Pageable pageable);

    Page<Event> findByContainerIdAndTypeOrderByTimestampDesc(Long containerId, EventType type, Pageable pageable);

    Page<Event> findByContainerIdAndSeverityOrderByTimestampDesc(Long containerId, EventSeverity severity, Pageable pageable);

    Page<Event> findByContainerIdAndTypeAndSeverityOrderByTimestampDesc(
            Long containerId, EventType type, EventSeverity severity, Pageable pageable);
}
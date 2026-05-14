package com.example.cryoguard.evaluation.infrastructure.persistence;

import com.example.cryoguard.evaluation.domain.entities.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByAlertId(String alertId);

    List<Alert> findByContainerId(Long containerId);

    Page<Alert> findAll(Pageable pageable);

    @Query("SELECT a FROM Alert a WHERE " +
           "(:severity IS NULL OR LOWER(CAST(a.severity AS string)) = LOWER(:severity)) AND " +
           "(:status IS NULL OR " +
           "  (:status = 'open' AND a.resolved = false) OR " +
           "  (:status = 'resolved' AND a.resolved = true))")
    Page<Alert> findByFilters(@Param("severity") String severity, @Param("status") String status, Pageable pageable);

    List<Alert> findByResolvedFalseOrderByTimestampDesc();

    List<Alert> findByResolvedTrueOrderByTimestampDesc();
}
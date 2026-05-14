package com.example.cryoguard.monitoring.infrastructure.persistence;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    Optional<Container> findByContainerId(String containerId);
    boolean existsByContainerId(String containerId);
}
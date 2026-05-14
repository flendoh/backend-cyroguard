package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.domain.valueobjects.ContainerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContainerQueryService {
    Container getContainerById(Long id);
    Page<Container> getAllContainers(Pageable pageable);
    Page<Container> getAllContainers(ContainerStatus status, String productType, Pageable pageable);
    Optional<Container> findById(Long id);
}
package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.aggregates.Container;

import java.util.List;

public interface ContainerQueryService {
    Container getContainerById(Long id);
    List<Container> getAllContainers();
}
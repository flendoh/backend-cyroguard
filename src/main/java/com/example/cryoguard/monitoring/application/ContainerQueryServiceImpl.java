package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.infrastructure.persistence.ContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContainerQueryServiceImpl implements ContainerQueryService {

    private final ContainerRepository containerRepository;

    @Override
    public Container getContainerById(Long id) {
        return containerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Container not found: " + id));
    }

    @Override
    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }
}
package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.domain.valueobjects.ContainerStatus;
import com.example.cryoguard.monitoring.infrastructure.persistence.ContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<Container> getAllContainers(Pageable pageable) {
        return containerRepository.findAllActive(pageable);
    }

    @Override
    public Page<Container> getAllContainers(ContainerStatus status, String productType, Pageable pageable) {
        if (status != null && productType != null) {
            return containerRepository.findByStatusAndProductType(status, productType, pageable);
        } else if (status != null) {
            return containerRepository.findByStatus(status, pageable);
        } else if (productType != null) {
            return containerRepository.findByProductType(productType, pageable);
        }
        return containerRepository.findAllActive(pageable);
    }

    @Override
    public Optional<Container> findById(Long id) {
        return containerRepository.findById(id);
    }
}
package com.example.cryoguard.monitoring.application;

import com.example.cryoguard.monitoring.domain.aggregates.Container;
import com.example.cryoguard.monitoring.domain.commands.CreateContainerCommand;
import com.example.cryoguard.monitoring.domain.commands.SyncContainerCommand;
import com.example.cryoguard.monitoring.domain.commands.UpdateContainerTelemetryCommand;
import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;
import com.example.cryoguard.monitoring.domain.valueobjects.ConnectivityStatus;
import com.example.cryoguard.monitoring.infrastructure.persistence.ContainerRepository;
import com.example.cryoguard.monitoring.infrastructure.persistence.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContainerCommandServiceImpl implements ContainerCommandService {

    private final ContainerRepository containerRepository;
    private final TelemetryRepository telemetryRepository;

    @Override
    @Transactional
    public Container createContainer(CreateContainerCommand command) {
        Container container = new Container();
        container.setContainerId(command.getContainerId());
        container.setName(command.getName());
        container.setDeviceId(command.getDeviceId());
        container.setTemperatureMin(command.getTemperatureMin());
        container.setTemperatureMax(command.getTemperatureMax());
        container.setHumidityMin(command.getHumidityMin());
        container.setHumidityMax(command.getHumidityMax());
        container.setConnectivity(ConnectivityStatus.OFFLINE);
        return containerRepository.save(container);
    }

    @Override
    @Transactional
    public Container updateContainer(Long id, CreateContainerCommand command) {
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Container not found: " + id));
        container.setContainerId(command.getContainerId());
        container.setName(command.getName());
        container.setDeviceId(command.getDeviceId());
        container.setTemperatureMin(command.getTemperatureMin());
        container.setTemperatureMax(command.getTemperatureMax());
        container.setHumidityMin(command.getHumidityMin());
        container.setHumidityMax(command.getHumidityMax());
        return containerRepository.save(container);
    }

    @Override
    @Transactional
    public void deleteContainer(Long id) {
        containerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TelemetryReading recordTelemetry(UpdateContainerTelemetryCommand command) {
        Container container = containerRepository.findById(command.getContainerId())
                .orElseThrow(() -> new IllegalArgumentException("Container not found: " + command.getContainerId()));

        TelemetryReading reading = new TelemetryReading();
        reading.setContainerId(command.getContainerId());
        reading.setTimestamp(command.getTimestamp() != null ? command.getTimestamp() : LocalDateTime.now());
        reading.setTemperature(command.getTemperature());
        reading.setHumidity(command.getHumidity());
        reading.setVibration(command.getVibration());
        reading.setDoorOpen(command.getDoorOpen());
        reading.setLatitude(command.getLatitude());
        reading.setLongitude(command.getLongitude());

        container.setCurrentTemperature(command.getTemperature());
        container.setCurrentHumidity(command.getHumidity());
        container.setCurrentVibration(command.getVibration());
        container.setGpsLatitude(command.getLatitude());
        container.setGpsLongitude(command.getLongitude());
        container.setBatteryLevel(command.getBatteryLevel());
        container.setLastSync(LocalDateTime.now());
        container.setConnectivity(ConnectivityStatus.ONLINE);
        container.updateStatus();

        containerRepository.save(container);
        return telemetryRepository.save(reading);
    }

    @Override
    @Transactional
    public Container syncContainer(SyncContainerCommand command) {
        Container container = containerRepository.findById(command.getContainerId())
                .orElseThrow(() -> new IllegalArgumentException("Container not found: " + command.getContainerId()));
        container.setGpsLatitude(command.getLatitude());
        container.setGpsLongitude(command.getLongitude());
        container.setLastSync(LocalDateTime.now());
        container.setConnectivity(ConnectivityStatus.ONLINE);
        container.updateStatus();
        return containerRepository.save(container);
    }
}
package com.example.cryoguard.monitoring.presentation.assemblers;

import com.example.cryoguard.monitoring.domain.entities.TelemetryReading;
import com.example.cryoguard.monitoring.presentation.resources.TelemetryReadingResource;
import org.springframework.stereotype.Component;

@Component
public class TelemetryReadingResourceAssembler {

    public TelemetryReadingResource toResource(TelemetryReading reading) {
        return new TelemetryReadingResource(
                reading.getId(),
                reading.getContainerId(),
                reading.getTimestamp(),
                reading.getTemperature(),
                reading.getHumidity(),
                reading.getVibration(),
                reading.getDoorOpen(),
                reading.getLatitude(),
                reading.getLongitude(),
                reading.getBatteryLevel()
        );
    }
}
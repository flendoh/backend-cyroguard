package com.example.cryoguard.logistics.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GeofenceStatus {
    active("active"),
    inactive("inactive");

    private final String value;

    GeofenceStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
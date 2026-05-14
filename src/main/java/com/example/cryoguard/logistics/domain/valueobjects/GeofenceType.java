package com.example.cryoguard.logistics.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GeofenceType {
    circle("circle"),
    polygon("polygon");

    private final String value;

    GeofenceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
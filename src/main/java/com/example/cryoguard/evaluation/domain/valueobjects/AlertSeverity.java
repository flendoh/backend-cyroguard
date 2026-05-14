package com.example.cryoguard.evaluation.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AlertSeverity {
    CRITICAL("critical"),
    WARNING("warning"),
    INFO("info");

    private final String value;

    AlertSeverity(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static AlertSeverity fromValue(String value) {
        for (AlertSeverity severity : AlertSeverity.values()) {
            if (severity.value.equalsIgnoreCase(value)) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Unknown severity: " + value);
    }

    public AlertSeverity escalate() {
        return switch (this) {
            case INFO -> WARNING;
            case WARNING -> CRITICAL;
            case CRITICAL -> null;
        };
    }
}
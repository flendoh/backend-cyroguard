package com.example.cryoguard.monitoring.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GpsCoordinates {

    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;   // -90 to 90

    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;  // -180 to 180
}
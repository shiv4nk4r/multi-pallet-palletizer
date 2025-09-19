package com.shivankar.capacitypalletsoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the depot/warehouse where vehicles start and end their routes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depot {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    
    public double getDistanceTo(Customer customer) {
        double deltaLat = this.latitude - customer.getLatitude();
        double deltaLon = this.longitude - customer.getLongitude();
        return Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon) * 111.0;
    }
}
package com.shivankar.capacitypalletsoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer location with demand (renamed from Stops for clarity)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private int demand; // The amount of goods to deliver
    
    public double getDistanceTo(Customer other) {
        // Simple Euclidean distance - in real scenarios, you'd use proper geospatial calculations
        double deltaLat = this.latitude - other.latitude;
        double deltaLon = this.longitude - other.longitude;
        return Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon) * 111.0; // Convert to approximate km
    }
    
    public double getDistanceTo(Depot depot) {
        double deltaLat = this.latitude - depot.getLatitude();
        double deltaLon = this.longitude - depot.getLongitude();
        return Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon) * 111.0;
    }
}

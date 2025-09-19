package com.shivankar.capacitypalletsoptimizer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningListVariable;

import java.util.List;

/**
 * Represents a delivery vehicle with a route of customers to visit
 */
@Data
@NoArgsConstructor
@PlanningEntity
public class Vehicle {
    private Long id;
    private String name;
    private int capacity; // Maximum capacity of the vehicle
    private Depot depot; // Starting and ending point
    
    @PlanningListVariable
    private List<Customer> customers; // The route - list of customers to visit in order
    
    public Vehicle(Long id, String name, int capacity, Depot depot) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.depot = depot;
    }
    
    /**
     * Calculate total demand for all customers in this vehicle's route
     */
    public int getTotalDemand() {
        if (customers == null) {
            return 0;
        }
        return customers.stream().mapToInt(Customer::getDemand).sum();
    }
    
    /**
     * Calculate total distance of the route including return to depot
     */
    public double getTotalDistance() {
        if (customers == null || customers.isEmpty()) {
            return 0.0;
        }
        
        double totalDistance = 0.0;
        
        // Distance from depot to first customer
        totalDistance += depot.getDistanceTo(customers.get(0));
        
        // Distance between consecutive customers
        for (int i = 0; i < customers.size() - 1; i++) {
            totalDistance += customers.get(i).getDistanceTo(customers.get(i + 1));
        }
        
        // Distance from last customer back to depot
        Customer lastCustomer = customers.get(customers.size() - 1);
        totalDistance += lastCustomer.getDistanceTo(depot);
        
        return totalDistance;
    }
}

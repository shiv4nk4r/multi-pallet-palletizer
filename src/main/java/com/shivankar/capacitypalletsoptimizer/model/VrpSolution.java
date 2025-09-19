package com.shivankar.capacitypalletsoptimizer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.List;

/**
 * Main solution class for the Vehicle Routing Problem (VRP)
 * Contains all the problem data and the solution (vehicle routes)
 */
@Data
@NoArgsConstructor
@PlanningSolution
public class VrpSolution {
    
    private String name;
    
    @ProblemFactCollectionProperty
    private List<Depot> depots;
    
    @PlanningEntityCollectionProperty
    private List<Vehicle> vehicles;
    
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Customer> customers;
    
    @PlanningScore
    private HardSoftLongScore score;
    
    public VrpSolution(String name, List<Depot> depots, List<Vehicle> vehicles, List<Customer> customers) {
        this.name = name;
        this.depots = depots;
        this.vehicles = vehicles;
        this.customers = customers;
    }
    
    /**
     * Calculate the total distance of all vehicle routes
     */
    public double getTotalDistance() {
        return vehicles.stream()
                .mapToDouble(Vehicle::getTotalDistance)
                .sum();
    }
    
    /**
     * Get the total number of customers served
     */
    public int getTotalCustomersServed() {
        return vehicles.stream()
                .mapToInt(vehicle -> vehicle.getCustomers() == null ? 0 : vehicle.getCustomers().size())
                .sum();
    }
}
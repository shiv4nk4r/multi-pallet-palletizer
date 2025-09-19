package com.shivankar.capacitypalletsoptimizer.solver;

import com.shivankar.capacitypalletsoptimizer.model.Vehicle;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

/**
 * Constraint provider for the Vehicle Routing Problem using OptaPlanner's constraint streams
 */
public class VrpConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                // Hard constraints
                vehicleCapacity(constraintFactory),
                
                // Soft constraints
                minimizeDistance(constraintFactory)
        };
    }

    /**
     * Hard constraint: Vehicle capacity must not be exceeded
     * Each vehicle has a maximum capacity and the sum of customer demands cannot exceed it
     */
    private Constraint vehicleCapacity(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Vehicle.class)
                .filter(vehicle -> vehicle.getTotalDemand() > vehicle.getCapacity())
                .penalize(HardSoftLongScore.ONE_HARD,
                        vehicle -> vehicle.getTotalDemand() - vehicle.getCapacity())
                .asConstraint("Vehicle capacity");
    }

    /**
     * Soft constraint: Minimize total travel distance
     * We want to minimize the total distance traveled by all vehicles
     */
    private Constraint minimizeDistance(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Vehicle.class)
                .filter(vehicle -> vehicle.getCustomers() != null && !vehicle.getCustomers().isEmpty())
                .penalizeLong(HardSoftLongScore.ONE_SOFT,
                        vehicle -> (long) (vehicle.getTotalDistance() * 1000)) // Multiply by 1000 for better precision
                .asConstraint("Minimize distance");
    }


}
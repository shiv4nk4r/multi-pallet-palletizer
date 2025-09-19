package com.shivankar.capacitypalletsoptimizer.controller;

import com.shivankar.capacitypalletsoptimizer.model.*;
import com.shivankar.capacitypalletsoptimizer.service.VrpSolverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Vehicle Routing Problem optimization
 */
@RestController
@RequestMapping("/api/vrp")
@RequiredArgsConstructor
public class VrpController {

    private final VrpSolverService vrpSolverService;

    /**
     * Solve a VRP problem with the provided data
     */
    @PostMapping("/solve")
    public ResponseEntity<VrpSolution> solve(@RequestBody VrpRequest request) {
        VrpSolution problem = createVrpProblem(request);
        VrpSolution solution = vrpSolverService.solve(problem);
        return ResponseEntity.ok(solution);
    }

    /**
     * Get a sample VRP problem for testing
     */
    @GetMapping("/sample")
    public ResponseEntity<VrpSolution> getSampleProblem() {
        VrpSolution sampleProblem = createSampleProblem();
        return ResponseEntity.ok(sampleProblem);
    }

    /**
     * Solve the sample VRP problem
     */
    @GetMapping("/solve-sample")
    public ResponseEntity<VrpSolution> solveSample() {
        VrpSolution sampleProblem = createSampleProblem();
        VrpSolution solution = vrpSolverService.solve(sampleProblem);
        return ResponseEntity.ok(solution);
    }

    private VrpSolution createVrpProblem(VrpRequest request) {
        List<Depot> depots = List.of(request.getDepot());
        List<Vehicle> vehicles = request.getVehicles();
        List<Customer> customers = request.getCustomers();

        return new VrpSolution("VRP Problem", depots, vehicles, customers);
    }

    private VrpSolution createSampleProblem() {
        // Create depot
        Depot depot = new Depot(1L, "Main Depot", 0.0, 0.0);

        // Create customers
        List<Customer> customers = List.of(
                new Customer(1L, "Customer 1", 1.0, 1.0, 10),
                new Customer(2L, "Customer 2", 2.0, 2.0, 15),
                new Customer(3L, "Customer 3", 3.0, 1.0, 20),
                new Customer(4L, "Customer 4", 1.0, 3.0, 12),
                new Customer(5L, "Customer 5", 4.0, 4.0, 18)
        );

        // Create vehicles
        List<Vehicle> vehicles = List.of(
                new Vehicle(1L, "Vehicle 1", 50, depot),
                new Vehicle(2L, "Vehicle 2", 40, depot)
        );

        return new VrpSolution("Sample VRP", List.of(depot), vehicles, customers);
    }

    /**
     * Request object for VRP solving
     */
    public static class VrpRequest {
        private Depot depot;
        private List<Vehicle> vehicles;
        private List<Customer> customers;

        // Getters and setters
        public Depot getDepot() { return depot; }
        public void setDepot(Depot depot) { this.depot = depot; }
        public List<Vehicle> getVehicles() { return vehicles; }
        public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }
        public List<Customer> getCustomers() { return customers; }
        public void setCustomers(List<Customer> customers) { this.customers = customers; }
    }
}
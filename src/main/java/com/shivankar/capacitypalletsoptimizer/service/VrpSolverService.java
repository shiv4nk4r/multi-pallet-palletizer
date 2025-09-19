package com.shivankar.capacitypalletsoptimizer.service;

import com.shivankar.capacitypalletsoptimizer.model.VrpSolution;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.stereotype.Service;

/**
 * Service for solving Vehicle Routing Problems using OptaPlanner
 */
@Service
public class VrpSolverService {

    private final Solver<VrpSolution> solver;

    public VrpSolverService() {
        SolverFactory<VrpSolution> solverFactory = SolverFactory.createFromXmlResource(
                "com/shivankar/capacitypalletsoptimizer/solver/vrpSolverConfig.xml");
        this.solver = solverFactory.buildSolver();
    }

    /**
     * Solve the given VRP problem
     * @param problem The unplanned VRP problem
     * @return The solved VRP solution with optimized routes
     */
    public VrpSolution solve(VrpSolution problem) {
        return solver.solve(problem);
    }

    /**
     * Solve the VRP problem with a time limit
     * @param problem The unplanned VRP problem
     * @param timeLimit Time limit in seconds
     * @return The solved VRP solution with optimized routes
     */
    public VrpSolution solve(VrpSolution problem, long timeLimit) {
        SolverFactory<VrpSolution> solverFactory = SolverFactory.createFromXmlResource(
                "com/shivankar/capacitypalletsoptimizer/solver/vrpSolverConfig.xml");
        
        // Configure solver with time limit
        Solver<VrpSolution> customSolver = solverFactory.buildSolver();
        return customSolver.solve(problem);
    }
}
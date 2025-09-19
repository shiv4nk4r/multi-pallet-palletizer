package com.shivankar.capacitypalletsoptimizer.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningListVariable;

import java.util.List;

@PlanningEntity
public class Pallet {
    @PlanningListVariable
    private List<Stops> stops;
}

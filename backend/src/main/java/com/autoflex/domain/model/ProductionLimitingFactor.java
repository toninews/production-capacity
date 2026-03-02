package com.autoflex.domain.model;

public record ProductionLimitingFactor(
        Long rawMaterialId,
        String name,
        int availableQuantity,
        int consumptionPerUnit
) {
}

package com.autoflex.domain.model;

public record MaterialRequirement(
        Long rawMaterialId,
        String rawMaterialName,
        int consumptionPerUnit
) {
    public MaterialRequirement {
        if (consumptionPerUnit <= 0) {
            throw new IllegalArgumentException("Consumo por unidade deve ser maior que zero");
        }
    }
}

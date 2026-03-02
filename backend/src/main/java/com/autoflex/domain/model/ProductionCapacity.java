package com.autoflex.domain.model;

public record ProductionCapacity(
        Long productId,
        String productName,
        int producibleQuantity,
        ProductionLimitingFactor limitingFactor
) {
}

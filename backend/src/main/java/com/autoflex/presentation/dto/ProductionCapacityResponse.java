package com.autoflex.presentation.dto;

import com.autoflex.domain.model.ProductionCapacity;
import com.autoflex.domain.model.ProductionLimitingFactor;

public record ProductionCapacityResponse(
        Long productId,
        String productName,
        int producibleQuantity,
        LimitingFactorResponse limitingFactor
) {
    public static ProductionCapacityResponse fromDomain(ProductionCapacity capacity) {
        LimitingFactorResponse limitingFactor = capacity.limitingFactor() == null
                ? null
                : LimitingFactorResponse.fromDomain(capacity.limitingFactor());

        return new ProductionCapacityResponse(
                capacity.productId(),
                capacity.productName(),
                capacity.producibleQuantity(),
                limitingFactor
        );
    }

    public record LimitingFactorResponse(
            Long rawMaterialId,
            String name,
            int availableQuantity,
            int consumptionPerUnit
    ) {
        public static LimitingFactorResponse fromDomain(ProductionLimitingFactor factor) {
            return new LimitingFactorResponse(
                    factor.rawMaterialId(),
                    factor.name(),
                    factor.availableQuantity(),
                    factor.consumptionPerUnit()
            );
        }
    }
}

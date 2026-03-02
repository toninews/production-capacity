package com.autoflex.presentation.dto;

import com.autoflex.domain.model.ProductionCapacity;
import com.autoflex.domain.model.ProductionLimitingFactor;

public record ProductionCapacityResponse(
        Long produtoId,
        String nome,
        int quantidadeProduzivel,
        LimitingFactorResponse limitante
) {
    public static ProductionCapacityResponse fromDomain(ProductionCapacity capacity) {
        LimitingFactorResponse limitingFactor = capacity.limitante() == null
                ? null
                : LimitingFactorResponse.fromDomain(capacity.limitante());

        return new ProductionCapacityResponse(
                capacity.produtoId(),
                capacity.nome(),
                capacity.quantidadeProduzivel(),
                limitingFactor
        );
    }

    public record LimitingFactorResponse(
            Long materiaPrimaId,
            String nome,
            int disponivel,
            int consumoPorUnidade
    ) {
        public static LimitingFactorResponse fromDomain(ProductionLimitingFactor factor) {
            return new LimitingFactorResponse(
                    factor.materiaPrimaId(),
                    factor.nome(),
                    factor.disponivel(),
                    factor.consumoPorUnidade()
            );
        }
    }
}

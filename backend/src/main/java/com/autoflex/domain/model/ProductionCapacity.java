package com.autoflex.domain.model;

public record ProductionCapacity(
        Long produtoId,
        String nome,
        int quantidadeProduzivel,
        ProductionLimitingFactor limitante
) {
}

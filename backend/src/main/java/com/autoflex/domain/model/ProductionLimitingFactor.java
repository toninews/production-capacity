package com.autoflex.domain.model;

public record ProductionLimitingFactor(
        Long materiaPrimaId,
        String nome,
        int disponivel,
        int consumoPorUnidade
) {
}

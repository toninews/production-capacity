package com.autoflex.domain.model;

public record MaterialRequirement(
        Long materiaPrimaId,
        String materiaPrimaNome,
        int consumoPorUnidade
) {
    public MaterialRequirement {
        if (consumoPorUnidade <= 0) {
            throw new IllegalArgumentException("Consumo por unidade deve ser maior que zero");
        }
    }
}

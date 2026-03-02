package com.autoflex.domain.model;

public record RawMaterialStock(
        Long materiaPrimaId,
        String nome,
        int quantidadeDisponivel
) {
}

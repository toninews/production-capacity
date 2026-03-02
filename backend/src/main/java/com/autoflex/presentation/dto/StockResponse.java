package com.autoflex.presentation.dto;

import com.autoflex.domain.model.RawMaterialStock;

public record StockResponse(
        Long materiaPrimaId,
        String nome,
        int quantidadeDisponivel
) {
    public static StockResponse fromDomain(RawMaterialStock stock) {
        return new StockResponse(stock.materiaPrimaId(), stock.nome(), stock.quantidadeDisponivel());
    }
}

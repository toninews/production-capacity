package com.autoflex.presentation.dto;

import com.autoflex.domain.model.RawMaterialStock;

public record StockResponse(
        Long rawMaterialId,
        String name,
        int availableQuantity
) {
    public static StockResponse fromDomain(RawMaterialStock stock) {
        return new StockResponse(stock.rawMaterialId(), stock.name(), stock.availableQuantity());
    }
}

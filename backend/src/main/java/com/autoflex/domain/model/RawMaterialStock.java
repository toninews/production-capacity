package com.autoflex.domain.model;

public record RawMaterialStock(
        Long rawMaterialId,
        String name,
        int availableQuantity
) {
}

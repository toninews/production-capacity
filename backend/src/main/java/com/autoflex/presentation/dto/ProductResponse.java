package com.autoflex.presentation.dto;

import com.autoflex.domain.model.Product;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        List<RecipeItemResponse> recipe
) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.id(),
                product.name(),
                product.recipe().stream()
                        .map(item -> new RecipeItemResponse(
                                item.rawMaterialId(),
                                item.rawMaterialName(),
                                item.consumptionPerUnit()
                        ))
                        .toList()
        );
    }

    public record RecipeItemResponse(
            Long rawMaterialId,
            String name,
            int consumptionPerUnit
    ) {
    }
}

package com.autoflex.presentation.dto;

import com.autoflex.domain.model.Product;

import java.util.List;

public record ProductResponse(
        Long id,
        String nome,
        List<RecipeItemResponse> receita
) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.id(),
                product.nome(),
                product.receita().stream()
                        .map(item -> new RecipeItemResponse(
                                item.materiaPrimaId(),
                                item.materiaPrimaNome(),
                                item.consumoPorUnidade()
                        ))
                        .toList()
        );
    }

    public record RecipeItemResponse(
            Long materiaPrimaId,
            String nome,
            int consumoPorUnidade
    ) {
    }
}

package com.autoflex.domain.service;

import com.autoflex.domain.model.MaterialRequirement;
import com.autoflex.domain.model.Product;
import com.autoflex.domain.model.RawMaterialStock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductionCalculatorTest {

    private final ProductionCalculator calculator = new ProductionCalculator();

    @Test
    void shouldCalculateProductionBasedOnTheMostRestrictiveMaterial() {
        Product product = new Product(1L, "Produto Teste", List.of(
                new MaterialRequirement(1L, "Aco", 2),
                new MaterialRequirement(2L, "Parafuso", 3)
        ));

        Map<Long, RawMaterialStock> stockById = Map.of(
                1L, new RawMaterialStock(1L, "Aco", 20),
                2L, new RawMaterialStock(2L, "Parafuso", 10)
        );

        var result = calculator.calculate(product, stockById);

        assertEquals(3, result.producibleQuantity());
        assertEquals(2L, result.limitingFactor().rawMaterialId());
    }

    @Test
    void shouldReturnZeroWhenAnyMaterialIsMissingFromStock() {
        Product product = new Product(1L, "Produto Teste", List.of(
                new MaterialRequirement(1L, "Aco", 2),
                new MaterialRequirement(3L, "Plastico", 1)
        ));

        Map<Long, RawMaterialStock> stockById = Map.of(
                1L, new RawMaterialStock(1L, "Aco", 20)
        );

        var result = calculator.calculate(product, stockById);

        assertEquals(0, result.producibleQuantity());
        assertEquals(3L, result.limitingFactor().rawMaterialId());
    }

    @Test
    void shouldRejectZeroConsumptionRequirement() {
        assertThrows(IllegalArgumentException.class, () ->
                new MaterialRequirement(1L, "Aco", 0)
        );
    }
}

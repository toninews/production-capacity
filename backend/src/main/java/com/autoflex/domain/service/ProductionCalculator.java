package com.autoflex.domain.service;

import com.autoflex.domain.model.MaterialRequirement;
import com.autoflex.domain.model.ProductionCapacity;
import com.autoflex.domain.model.ProductionLimitingFactor;
import com.autoflex.domain.model.Product;
import com.autoflex.domain.model.RawMaterialStock;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class ProductionCalculator {

    public ProductionCapacity calculate(Product product, Map<Long, RawMaterialStock> stockById) {
        if (product.recipe() == null || product.recipe().isEmpty()) {
            return new ProductionCapacity(product.id(), product.name(), 0, null);
        }

        List<MaterialCapacity> capacities = product.recipe().stream()
                .map(requirement -> toMaterialCapacity(requirement, stockById))
                .toList();

        MaterialCapacity limiting = capacities.stream()
                .min(Comparator.comparingInt(MaterialCapacity::maxUnits))
                .orElseThrow();

        ProductionLimitingFactor factor = new ProductionLimitingFactor(
                limiting.requirement().rawMaterialId(),
                limiting.requirement().rawMaterialName(),
                limiting.available(),
                limiting.requirement().consumptionPerUnit()
        );

        return new ProductionCapacity(product.id(), product.name(), limiting.maxUnits(), factor);
    }

    private MaterialCapacity toMaterialCapacity(MaterialRequirement requirement, Map<Long, RawMaterialStock> stockById) {
        RawMaterialStock stock = stockById.get(requirement.rawMaterialId());
        int available = stock != null ? stock.availableQuantity() : 0;
        int maxUnits = available / requirement.consumptionPerUnit();
        return new MaterialCapacity(requirement, available, maxUnits);
    }

    private record MaterialCapacity(MaterialRequirement requirement, int available, int maxUnits) {
    }
}

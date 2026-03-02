package com.autoflex.application.usecase;

import com.autoflex.application.port.ProductRepository;
import com.autoflex.application.port.StockRepository;
import com.autoflex.domain.model.ProductionCapacity;
import com.autoflex.domain.service.ProductionCalculator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ListPossibleProductionUseCase {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductionCalculator productionCalculator;

    public ListPossibleProductionUseCase(
            ProductRepository productRepository,
            StockRepository stockRepository,
            ProductionCalculator productionCalculator
    ) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.productionCalculator = productionCalculator;
    }

    public List<ProductionCapacity> execute() {
        var stockById = stockRepository.findAllByMaterialId();
        return productRepository.findAll().stream()
                .map(product -> productionCalculator.calculate(product, stockById))
                .sorted(Comparator.comparing(ProductionCapacity::productName))
                .toList();
    }
}

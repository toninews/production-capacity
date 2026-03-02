package com.autoflex.presentation.controller;

import com.autoflex.application.port.ProductRepository;
import com.autoflex.application.port.StockRepository;
import com.autoflex.application.usecase.ListPossibleProductionUseCase;
import com.autoflex.presentation.dto.ProductResponse;
import com.autoflex.presentation.dto.ProductionCapacityResponse;
import com.autoflex.presentation.dto.StockResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductionController {

    private final ListPossibleProductionUseCase listPossibleProductionUseCase;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public ProductionController(
            ListPossibleProductionUseCase listPossibleProductionUseCase,
            ProductRepository productRepository,
            StockRepository stockRepository
    ) {
        this.listPossibleProductionUseCase = listPossibleProductionUseCase;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @GetMapping("/production-capacity")
    public List<ProductionCapacityResponse> listPossibleProduction() {
        return listPossibleProductionUseCase.execute().stream()
                .map(ProductionCapacityResponse::fromDomain)
                .toList();
    }

    @GetMapping("/products")
    public List<ProductResponse> listProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/raw-material-stocks")
    public List<StockResponse> listStock() {
        return stockRepository.findAll().stream()
                .map(StockResponse::fromDomain)
                .toList();
    }
}

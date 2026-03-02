package com.autoflex.infrastructure.persistence;

import com.autoflex.application.port.StockRepository;
import com.autoflex.domain.model.RawMaterialStock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class InMemoryStockRepository implements StockRepository {

    private final List<RawMaterialStock> stock = List.of(
            new RawMaterialStock(1L, "Aco", 20),
            new RawMaterialStock(2L, "Parafuso", 7),
            new RawMaterialStock(3L, "Plastico", 11),
            new RawMaterialStock(4L, "Borracha", 2)
    );

    @Override
    public List<RawMaterialStock> findAll() {
        return stock;
    }

    @Override
    public Map<Long, RawMaterialStock> findAllByMaterialId() {
        return stock.stream().collect(Collectors.toMap(RawMaterialStock::materiaPrimaId, Function.identity()));
    }
}

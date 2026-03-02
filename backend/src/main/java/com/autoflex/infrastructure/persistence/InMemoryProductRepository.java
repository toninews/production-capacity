package com.autoflex.infrastructure.persistence;

import com.autoflex.application.port.ProductRepository;
import com.autoflex.domain.model.MaterialRequirement;
import com.autoflex.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = List.of(
            new Product(10L, "Produto A", List.of(
                    new MaterialRequirement(1L, "Aco", 2),
                    new MaterialRequirement(2L, "Parafuso", 1)
            )),
            new Product(20L, "Produto B", List.of(
                    new MaterialRequirement(1L, "Aco", 3),
                    new MaterialRequirement(3L, "Plastico", 2)
            )),
            new Product(30L, "Produto C", List.of(
                    new MaterialRequirement(2L, "Parafuso", 4),
                    new MaterialRequirement(4L, "Borracha", 1)
            )),
            new Product(40L, "Produto D", List.of(
                    new MaterialRequirement(5L, "Cobre", 2)
            ))
    );

    @Override
    public List<Product> findAll() {
        return products;
    }
}

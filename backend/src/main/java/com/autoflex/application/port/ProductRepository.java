package com.autoflex.application.port;

import com.autoflex.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}

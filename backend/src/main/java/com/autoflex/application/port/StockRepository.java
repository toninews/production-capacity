package com.autoflex.application.port;

import com.autoflex.domain.model.RawMaterialStock;

import java.util.List;
import java.util.Map;

public interface StockRepository {
    List<RawMaterialStock> findAll();

    Map<Long, RawMaterialStock> findAllByMaterialId();
}

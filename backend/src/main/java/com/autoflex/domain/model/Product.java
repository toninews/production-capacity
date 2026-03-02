package com.autoflex.domain.model;

import java.util.List;

public record Product(
        Long id,
        String name,
        List<MaterialRequirement> recipe
) {
}

package com.zachdonnelly.product.dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String name, String description, String category, Integer quantity, BigDecimal price) {
}

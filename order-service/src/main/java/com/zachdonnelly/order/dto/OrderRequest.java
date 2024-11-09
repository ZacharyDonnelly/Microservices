package com.zachdonnelly.order.dto;

public record OrderRequest(String id, String customerId, String orderId, String productName, Integer quantity) {
}

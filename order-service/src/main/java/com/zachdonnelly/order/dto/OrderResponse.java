package com.zachdonnelly.order.dto;

public record OrderResponse(String id, String customerId, String orderId, String productName, Integer quantity) {
}

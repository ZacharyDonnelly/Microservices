package com.zachdonnelly.order.services;

import com.zachdonnelly.order.dto.OrderRequest;
import com.zachdonnelly.order.dto.OrderResponse;
import com.zachdonnelly.order.models.Order;
import com.zachdonnelly.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public void deleteOrder(String orderId) {
        orderRepository.deleteByOrderId(orderId);
        log.info("Order - " + orderId + " deleted successfully!");
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.customerId())
                .orderId(orderRequest.orderId())
                .productName(orderRequest.productName())
                .quantity(orderRequest.quantity())
                .build();

        orderRepository.save(order);

        log.info("Order - " + orderRequest.orderId() + " created successfully!");
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getOrderId(),
                order.getProductName(),
                order.getQuantity()
        );
    }
}

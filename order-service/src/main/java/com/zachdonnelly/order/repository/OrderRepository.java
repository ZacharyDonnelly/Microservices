package com.zachdonnelly.order.repository;

import com.zachdonnelly.order.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByOrderId(String orderId);
    List<Order> findByCustomerId(String customerId);

    void deleteByOrderId(String orderId);
}

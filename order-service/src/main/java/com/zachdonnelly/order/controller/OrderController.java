package com.zachdonnelly.order.controller;

import com.zachdonnelly.order.client.Client;
import com.zachdonnelly.order.dto.OrderRequest;
import com.zachdonnelly.order.dto.OrderResponse;
import com.zachdonnelly.order.model.Product;
import com.zachdonnelly.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final Client client;

    public OrderController(OrderService orderService, Client client) {
        this.orderService = orderService;
        this.client = client;
    }

    @PostMapping("/orders/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @DeleteMapping("/orders/delete/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId) {
        try {
            orderService.deleteOrder(orderId);
        } catch (Exception e) {
            throw new RuntimeException("Order not found");
        }
    }

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody Product productRequest) {
        try {
            client.createProduct(productRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product!");
        }
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts() {
        return client.getAllProducts();
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable String id) {
        return client.getProductById(id);
    }
}

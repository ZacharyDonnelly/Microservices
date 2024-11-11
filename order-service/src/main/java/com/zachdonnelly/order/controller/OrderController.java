package com.zachdonnelly.order.controller;

import com.zachdonnelly.order.dto.OrderRequest;
import com.zachdonnelly.order.dto.OrderResponse;
import com.zachdonnelly.order.model.Product;
import com.zachdonnelly.order.service.OrderService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final RestTemplate restTemplate;


    private static HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    public OrderController(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
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
    public String createProduct(@RequestBody Product productRequest) throws RestClientException {
        String baseUrl = "http://PRODUCT-SERVICE/products";
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.postForEntity(baseUrl, productRequest, String.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error creating product!");
        }

        return Objects.requireNonNull(response).getBody();
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllProducts() {
        String baseUrl = "http://PRODUCT-SERVICE/products";
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.getForEntity(baseUrl, String.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error creating product!");
        }

        return Objects.requireNonNull(response).getBody();
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProductById(@PathVariable String id) {
        String baseUrl = "http://PRODUCT-SERVICE/products";
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.getForEntity(baseUrl + "/" + id, String.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error creating product!");
        }

        return Objects.requireNonNull(response).getBody();
    }
}

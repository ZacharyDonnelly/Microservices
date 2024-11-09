package com.zachdonnelly.order.client;

import com.zachdonnelly.order.models.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;


public interface Client {

    @GetExchange("/products")
    List<Product> getAllProducts();

    @GetExchange("/products/{id}")
    Product getProductById(@PathVariable String id);

    @PostExchange("/products")
    void createProduct(@RequestBody Product productRequest);
}

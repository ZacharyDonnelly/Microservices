package com.zachdonnelly.product.repository;

import com.zachdonnelly.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);

    List<Product> findByCategory(String category);
}

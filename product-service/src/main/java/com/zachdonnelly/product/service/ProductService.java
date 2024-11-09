package com.zachdonnelly.product.service;

import com.zachdonnelly.product.dto.ProductRequest;
import com.zachdonnelly.product.dto.ProductResponse;
import com.zachdonnelly.product.model.Product;
import com.zachdonnelly.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .category(productRequest.category())
                .quantity(productRequest.quantity())
                .price(productRequest.price())
                .build();

        productRepository.save(product);

        log.info("Product - " + productRequest.name() + " created successfully!" );

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getQuantity(),
                        product.getPrice()
                ))
                .toList();
    }

    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByName(name);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
        log.info("Product - " + id + " deleted successfully!" );
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow();
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public List<ProductResponse> getProductsByCategory(String category) {
        List<Product> productCategory = productRepository.findByCategory(category);
        return productCategory
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getQuantity(),
                        product.getPrice()
                ))
                .toList();
    }
}

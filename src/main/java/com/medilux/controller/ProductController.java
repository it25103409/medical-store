package com.medilux.controller;

import com.medilux.model.Product;
import com.medilux.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            if (product.getName() == null || product.getName().isBlank())
                return ResponseEntity.badRequest().body(Map.of("message", "Product name is required"));

            productRepository.save(product);
            return ResponseEntity.ok(Map.of("message", "Product added successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isPresent()) return ResponseEntity.ok(productOpt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String q) {
        try {
            if (q == null || q.isBlank())
                return ResponseEntity.ok(productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
            return ResponseEntity.ok(productRepository.findByNameContainingOrDescriptionContainingOrderByNameAsc(q, q));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable int categoryId) {
        try {
            return ResponseEntity.ok(productRepository.findByCategoryIdOrderByNameAsc(categoryId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockProducts(@RequestParam(defaultValue = "10") int threshold) {
        try {
            return ResponseEntity.ok(productRepository.findByStockLessThanEqualOrderByStockAsc(threshold));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getProductCount() {
        try {
            return ResponseEntity.ok(Map.of("count", productRepository.count()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product productDetails) {
        try {
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setName(productDetails.getName());
                product.setPrice(productDetails.getPrice());
                product.setCategoryId(productDetails.getCategoryId());
                product.setIcon(productDetails.getIcon());
                product.setDescription(productDetails.getDescription());
                product.setStock(productDetails.getStock());
                productRepository.save(product);
                return ResponseEntity.ok(Map.of("message", "Product updated successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        try {
            int qty = body.getOrDefault("quantity", 1);
            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                if (product.getStock() >= qty) {
                    product.setStock(product.getStock() - qty);
                    productRepository.save(product);
                    return ResponseEntity.ok(Map.of("message", "Stock reduced by " + qty));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Not enough stock"));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/restock")
    public ResponseEntity<?> restockProduct(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        try {
            int qty = body.getOrDefault("quantity", 1);
            if (qty <= 0)
                return ResponseEntity.badRequest().body(Map.of("message", "Quantity must be positive"));

            Optional<Product> productOpt = productRepository.findById(id);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setStock(product.getStock() + qty);
                productRepository.save(product);
                return ResponseEntity.ok(Map.of("message", "Stock increased by " + qty));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Product not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }
}

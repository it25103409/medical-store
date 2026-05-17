package com.medilux.repository;

import com.medilux.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryIdOrderByNameAsc(int categoryId);
    List<Product> findByNameContainingOrDescriptionContainingOrderByNameAsc(String name, String description);
    List<Product> findByStockLessThanEqualOrderByStockAsc(int stock);
}

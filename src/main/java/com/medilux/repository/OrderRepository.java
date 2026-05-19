package com.medilux.repository;

import com.medilux.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(String orderId);
    List<Order> findByUserIdOrderByIdDesc(int userId);
    List<Order> findAllByOrderByIdDesc();
    List<Order> findByStatusOrderByIdDesc(String status);
    void deleteByOrderId(String orderId);
}

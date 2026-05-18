package com.medilux.controller;

import com.medilux.model.Order;
import com.medilux.model.Product;
import com.medilux.repository.OrderRepository;
import com.medilux.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> body) {
        try {
            int userId = Integer.parseInt(body.get("userId").toString());
            String itemsJson = (String) body.get("items");
            String address = (String) body.get("address");
            double total = Double.parseDouble(body.get("total").toString());

            String orderId = "#ML-" + (10000 + (int)(Math.random() * 89999));
            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Order order = new Order();
            order.setOrderId(orderId);
            order.setUserId(userId);
            order.setItems(itemsJson);
            order.setAddress(address);
            order.setTotal(total);
            order.setStatus("pending");
            order.setOrderDate(currentDate);

            orderRepository.save(order);

            try {
                JSONArray items = new JSONArray(itemsJson);
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    int productId = item.optInt("id", -1);
                    int qty = item.optInt("quantity", 1);
                    if (productId > 0) {
                        Optional<Product> productOpt = productRepository.findById(productId);
                        if (productOpt.isPresent()) {
                            Product product = productOpt.get();
                            int newStock = Math.max(0, product.getStock() - qty);
                            product.setStock(newStock);
                            productRepository.save(product);
                        }
                    }
                }
            } catch (Exception ignored) {}

            return ResponseEntity.ok(Map.of("orderId", orderId, "message", "Order placed successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/track/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable String orderId) {
        try {
            Order order = orderRepository.findByOrderId(orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(orderRepository.findByUserIdOrderByIdDesc(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderRepository.findAllByOrderByIdDesc());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(orderRepository.findByStatusOrderByIdDesc(status));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
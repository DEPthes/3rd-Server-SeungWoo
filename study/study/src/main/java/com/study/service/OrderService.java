package com.study.service;

import com.study.domain.Product;
import com.study.domain.Order;

import java.util.List;

public class OrderService {

    public Order createOrder(List<Product> products) {
        Long orderId = generateOrderId();
        return new Order(orderId, products);
    }

    // 주문 ID 생성
    private Long generateOrderId() {
        return System.currentTimeMillis();
    }
}

package com.example.shopapp.repository;

import com.example.shopapp.controller.OrderDetailController;
import com.example.shopapp.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderId(int orderId);
}

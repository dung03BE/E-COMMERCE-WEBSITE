package com.example.ecommercewebsite.repository;

import com.example.ecommercewebsite.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderId(int orderId);
}

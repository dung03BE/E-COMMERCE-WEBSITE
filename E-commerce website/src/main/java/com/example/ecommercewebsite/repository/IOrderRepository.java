package com.example.ecommercewebsite.repository;

import com.example.ecommercewebsite.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IOrderRepository extends JpaRepository<Order,Integer> , JpaSpecificationExecutor<Order> {

    Order findByUserId(int userId);
}

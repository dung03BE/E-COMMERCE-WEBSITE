package com.example.shopapp.repository;

import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IOrderRepository extends JpaRepository<Order,Integer> , JpaSpecificationExecutor<Order> {

    Order findByUserId(int userId);
}

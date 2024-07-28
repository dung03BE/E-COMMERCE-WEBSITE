package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.Order;
import com.example.ecommercewebsite.form.CreatingOrderForm;
import com.example.ecommercewebsite.form.OrderFilterForm;
import com.example.ecommercewebsite.form.UpdateOrderForm;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders(OrderFilterForm form);

    Order createOrder(CreatingOrderForm form) throws DataNotFoundException;

    Order updateOrder(UpdateOrderForm form) throws DataNotFoundException;

    void deleteOrder(int id) throws DataNotFoundException;

    Order getOrderById(int id) throws DataNotFoundException;

    Order getOrderByUserId(int userId) throws DataNotFoundException;
}

package com.example.shopapp.service;

import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.Order;
import com.example.shopapp.form.CreatingOrderForm;
import com.example.shopapp.form.OrderFilterForm;
import com.example.shopapp.form.UpdateOrderForm;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders(OrderFilterForm form);

    Order createOrder(CreatingOrderForm form) throws DataNotFoundException;

    Order updateOrder(UpdateOrderForm form) throws DataNotFoundException;

    void deleteOrder(int id) throws DataNotFoundException;

    Order getOrderById(int id) throws DataNotFoundException;

    Order getOrderByUserId(int userId) throws DataNotFoundException;
}

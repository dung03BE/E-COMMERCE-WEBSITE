package com.example.shopapp.service;

import com.example.shopapp.DTO.OrderDetailDTO;
import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.OrderDetail;
import com.example.shopapp.form.CreatingOrderDetailForm;
import com.example.shopapp.form.UpdateOrderDetailForm;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> getAllOrderDetails();

    OrderDetailDTO createOrderDetail(CreatingOrderDetailForm form) throws DataNotFoundException;

    

   

    OrderDetail upDateOD(UpdateOrderDetailForm form) throws DataNotFoundException;

    void deleteOD(int id);

    OrderDetail getOrderDetailById(int id) throws DataNotFoundException;

    List<OrderDetail> getOrderDetailByOrderId(int orderId) throws DataNotFoundException;
}

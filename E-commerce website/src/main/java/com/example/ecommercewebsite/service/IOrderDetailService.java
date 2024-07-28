package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.dto.OrderDetailDTO;
import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.OrderDetail;
import com.example.ecommercewebsite.form.CreatingOrderDetailForm;
import com.example.ecommercewebsite.form.UpdateOrderDetailForm;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetail> getAllOrderDetails();

    OrderDetailDTO createOrderDetail(CreatingOrderDetailForm form) throws DataNotFoundException;

    

   

    OrderDetail upDateOD(UpdateOrderDetailForm form) throws DataNotFoundException;

    void deleteOD(int id);

    OrderDetail getOrderDetailById(int id) throws DataNotFoundException;

    List<OrderDetail> getOrderDetailByOrderId(int orderId) throws DataNotFoundException;
}

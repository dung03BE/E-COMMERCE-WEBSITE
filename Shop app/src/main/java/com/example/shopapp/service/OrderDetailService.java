package com.example.shopapp.service;

import com.example.shopapp.dto.OrderDetailDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.OrderDetail;
import com.example.shopapp.entity.Product;
import com.example.shopapp.form.CreatingOrderDetailForm;
import com.example.shopapp.form.UpdateOrderDetailForm;
import com.example.shopapp.repository.IOrderDetailRepository;
import com.example.shopapp.repository.IOrderRepository;
import com.example.shopapp.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService{
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetailDTO createOrderDetail(CreatingOrderDetailForm form) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(form.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Dont find Order with id="+form.getOrderId()));
        Product existingProduct = productRepository.findById(form.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Dont find Product with id="+form.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(existingOrder)
                .product(existingProduct)
                .numberOfProduct(form.getNumberOfProduct())
                .totalMoney(form.getTotalMoney())
                .color(form.getColor())
                .price(form.getPrice())
                .build();
        orderDetailRepository.save(orderDetail);
        OrderDetailDTO orderDetailDTO;
        orderDetailDTO= modelMapper.map(orderDetail, OrderDetailDTO.class);
        return orderDetailDTO;
    }





    @Override
    public OrderDetail upDateOD(UpdateOrderDetailForm form) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(form.getId())
                .orElseThrow(()-> new DataNotFoundException("Dont find OrderDetail with id="+form.getId()));
        Order existingOrder = orderRepository.findById(form.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Dont find Order with id="+form.getOrderId()));
        Product existingProduct = productRepository.findById(form.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Dont find Product with id="+form.getProductId()));

        existingOrderDetail.setPrice(form.getPrice());
        existingOrderDetail.setNumberOfProduct(form.getNumberOfProduct());
        existingOrderDetail.setColor(form.getColor());
        existingOrderDetail.setTotalMoney(form.getTotalMoney());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteOD(int id) {
          orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetail getOrderDetailById(int id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Dont find OD with id:"+id));
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int orderId) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(()-> new DataNotFoundException("Dont find Order with id="+orderId));
        return orderDetailRepository.findByOrderId(orderId);
    }

}

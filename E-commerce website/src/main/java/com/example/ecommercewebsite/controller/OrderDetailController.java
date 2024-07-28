package com.example.ecommercewebsite.controller;

import com.example.ecommercewebsite.dto.OrderDetailDTO;
import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.OrderDetail;
import com.example.ecommercewebsite.form.CreatingOrderDetailForm;
import com.example.ecommercewebsite.form.UpdateOrderDetailForm;
import com.example.ecommercewebsite.service.IOrderDetailService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orderdetails")
public class OrderDetailController {
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping
    public List<OrderDetailDTO> getAllOrderDetails()
    {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return modelMapper.map(orderDetails,new TypeToken<List<OrderDetailDTO>>(){}.getType());
    }
    @PostMapping
    public ResponseEntity<?> createOrderDetails(@RequestBody CreatingOrderDetailForm form)
    {
        OrderDetailDTO orderDetailNew = null;
        try {
            orderDetailNew = orderDetailService.createOrderDetail(form);
            return ResponseEntity.ok().body(orderDetailNew);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    //tk by id
    @GetMapping("{id}")
    public  OrderDetailDTO getOrderDetailById(@PathVariable int id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        return modelMapper.map(orderDetail,OrderDetailDTO.class);
    }
    //tk by OrderId
    @GetMapping("/order/{orderId}")
    public List<OrderDetailDTO> getOrderDetailByOrderId(@PathVariable("orderId") int orderId) throws DataNotFoundException {
        List<OrderDetail> orderDetail = orderDetailService.getOrderDetailByOrderId(orderId);
        return modelMapper.map(orderDetail,new TypeToken<List<OrderDetailDTO>>(){}.getType());
    }
    //Update OrderDetail
    @PutMapping("{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable int id, @RequestBody UpdateOrderDetailForm form) throws DataNotFoundException {
        form.setId(id);
        OrderDetail updateOrderDetail = orderDetailService.upDateOD(form);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable int id)
    {
        orderDetailService.deleteOD(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

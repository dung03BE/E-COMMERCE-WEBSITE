package com.example.shopapp.controller;

import com.example.shopapp.DTO.OrderDTO;
import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.Order;
import com.example.shopapp.form.CreatingOrderForm;
import com.example.shopapp.form.OrderFilterForm;
import com.example.shopapp.form.UpdateOrderForm;
import com.example.shopapp.service.IOrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@Validated
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping
    public List<OrderDTO> getAllOrders(OrderFilterForm form)
    {
        List<Order> orders = orderService.getAllOrders(form);
        return modelMapper.map(orders,new TypeToken<List<OrderDTO>>(){}.getType());
    }
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid  @RequestBody CreatingOrderForm form) throws DataNotFoundException {
        Order orderNew = orderService.createOrder(form);
        return ResponseEntity.ok(orderNew);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int id,  @RequestBody UpdateOrderForm form) throws DataNotFoundException {
        form.setId(id);
        Order existingOrder = orderService.updateOrder(form);
        return new ResponseEntity<>("Update OK", HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) throws DataNotFoundException {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @GetMapping("{id}")
    public OrderDTO getOrderById(@PathVariable int id) throws DataNotFoundException {
        return modelMapper.map(orderService.getOrderById(id),OrderDTO.class);
    }
    @GetMapping("/user/{user_id}")
    public OrderDTO getOrderByUserId(@PathVariable("user_id") int userId) throws DataNotFoundException {
        Order orderByUserId = orderService.getOrderByUserId(userId);
        return modelMapper.map(orderByUserId,OrderDTO.class);
    }
}

package com.example.shopapp.service;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.Status;
import com.example.shopapp.entity.User;
import com.example.shopapp.form.CreatingOrderForm;
import com.example.shopapp.form.OrderFilterForm;
import com.example.shopapp.form.UpdateOrderForm;
import com.example.shopapp.repository.IOrderRepository;
import com.example.shopapp.repository.IUserRepository;
import com.example.shopapp.specification.OrderSpecification;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService{
    @Autowired
    private final IOrderRepository orderRepository;
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Override
    public List<Order> getAllOrders(OrderFilterForm form) {
        Specification<Order> where = OrderSpecification.buildWhere( form);
        return orderRepository.findAll(where);
    }

    @Override
    public Order createOrder(CreatingOrderForm form) throws DataNotFoundException {
            User user = userRepository.findById(form.getUserId())
                    .orElseThrow(()-> new DataNotFoundException("Don't find User with id="+form.getUserId()));

//        Order orderNew = new Order();
//        orderNew.setFullName(form.getFullName());
//        orderNew.setAddress(form.getAddress());
//        orderNew.setPhoneNumber(form.getPhoneNumber());
//        orderNew.setEmail(form.getEmail());
//        orderNew.setNote(form.getNote());
//        orderNew.setTotalMoney(form.getTotalMoney());
//        orderNew.setShippingAddress(form.getShippingAddress());
//        orderNew.setPaymentMethod(form.getPaymentMethod());
//        orderNew.setShippingDate(form.getShippingDate());
//        orderNew.setShippingMethod(form.getShippingMethod());
//        orderNew.setUser(user);
        //cach 2 modelmapper
//        TypeMap<CreatingOrderForm,Order> typeMap = modelMapper.getTypeMap(CreatingOrderForm.class,Order.class);
//        if(typeMap==null)
//        {
//            modelMapper.addMappings(new PropertyMap<CreatingOrderForm, Order>() {
//
//                @Override
//                protected void configure() {
//                    skip(destination.getId());
//                }
//            });
//        }
        modelMapper.typeMap(CreatingOrderForm.class,Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order  order =  new Order();
         modelMapper.map(form,order);
        order.setUser(user);
        order.setOrderDate(new Date());//time now
        order.setStatus(Status.pending);
        LocalDate shippingDate =form.getShippingDate()==null?
                LocalDate.now():form.getShippingDate();
        //shippingDate phải là từ ngày hôm nay
        if(shippingDate.isBefore(LocalDate.now()))
        {
            throw new DataNotFoundException("Data must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order updateOrder(UpdateOrderForm form) throws DataNotFoundException {
        Order order = orderRepository.findById(form.getId())
                .orElseThrow(()-> new DataNotFoundException("Dont find Order with id ="+form.getId()));
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(()-> new DataNotFoundException("Dont find User with id ="+form.getUserId()));
        Order existingOrder =modelMapper.map(form, Order.class);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(int id) throws DataNotFoundException {
        //k xóa cứng chỉ xóa mềm set Active =false;

        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Dont find Order with id ="+id));
        if(order!=null)
        {
            order.setActive(false);
            orderRepository.save(order);
        }
//        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrderById(int id) throws DataNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Dont find Order with id ="+id));
    }

    @Override
    public Order getOrderByUserId(int userId) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Dont find User with id="+userId));
        return orderRepository.findByUserId(userId);
    }

}

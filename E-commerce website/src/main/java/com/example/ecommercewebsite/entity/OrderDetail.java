package com.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.*;

;

@Entity
@Table(name="order_details")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @JoinColumn(name="price",nullable = false)
    private float price;
    @Column(name="number_of_products",nullable = false)
    private int numberOfProduct;
    @Column(name="total_money",nullable = false)
    private float totalMoney;
    private String color;

}

package com.example.shopapp.DTO;

import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDetailDTO {
    @JsonProperty("order_id")
    private int orderId;
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("price")
    private float price;
    @JsonProperty("number_of_products")
    private int numberOfProduct;
    @JsonProperty("total_money")
    private float totalMoney;
    private String color;
}

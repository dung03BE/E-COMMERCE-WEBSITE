package com.example.ecommercewebsite.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class UpdateOrderDetailForm {
    private int id;
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

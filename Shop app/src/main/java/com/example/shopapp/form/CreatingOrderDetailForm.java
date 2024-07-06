package com.example.shopapp.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CreatingOrderDetailForm {
    @NotBlank(message = "Order_Id is required!")
    @JsonProperty("order_id")
    private int orderId;
    @NotBlank(message = "ProductId is required!")
    @JsonProperty("product_id")
    private int productId;
    @Min(value = 0,message = "Price must be >=0")
    @JsonProperty("price")
    private float price;
    @Min(value = 0,message = "Quantity must be >=0")
    @JsonProperty("number_of_products")
    private int numberOfProduct;
    @Min(value = 0,message = "Total_money must be >=0")
    @JsonProperty("total_money")
    private float totalMoney;
    private String color;
}

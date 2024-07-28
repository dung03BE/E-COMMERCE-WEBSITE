package com.example.ecommercewebsite.dto;

import com.example.ecommercewebsite.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO extends BaseEntity {

    private String name;

    private float price;

    private String thumbnail ;
    private String description;

    private int categoryId;
}

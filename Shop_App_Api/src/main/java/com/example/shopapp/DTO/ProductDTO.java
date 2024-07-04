package com.example.shopapp.DTO;

import com.example.shopapp.entity.BaseEntity;
import com.example.shopapp.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

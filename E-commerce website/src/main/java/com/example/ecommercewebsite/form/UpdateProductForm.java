package com.example.ecommercewebsite.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateProductForm  {
    private int id;
    @NotBlank(message = "ProDuctName is required!")
    @Size(min=3, max=200,message = "ProductName must be between 3 and 200 characters")
    private String name;
    @Min(value =0 , message = "Price must be greater than or equal 0")
    @Max(value = 10000000, message = "Price must be less than or equal 10.000.000")
    private float price;

    private String thumbnail ;
    private String description;


    private int categoryId;
    private List<MultipartFile> files;
}

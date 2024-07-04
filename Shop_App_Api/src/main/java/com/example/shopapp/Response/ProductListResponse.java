package com.example.shopapp.Response;

import com.example.shopapp.DTO.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ProductListResponse {
    private List<ProductDTO> products;
    private int totalPages;
}

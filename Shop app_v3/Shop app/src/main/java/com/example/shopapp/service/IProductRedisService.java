package com.example.shopapp.service;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.form.ProductFilterForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductRedisService {
    void clear();
    List<ProductDTO> getAllProducts(Pageable pageable, ProductFilterForm form) throws JsonProcessingException;
    void saveAllProducts(List<ProductDTO> productDTOS, Pageable pageable) throws JsonProcessingException;
}

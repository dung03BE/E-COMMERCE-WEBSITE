package com.example.shopapp.service;

import com.example.shopapp.dto.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import com.example.shopapp.form.CreatingProductForm;
import com.example.shopapp.form.ProductFilterForm;
import com.example.shopapp.form.UpdateProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product createProduct(CreatingProductForm form) throws DataNotFoundException;


    Page<Product> getAllProducts(Pageable pageable, ProductFilterForm form);

    Product getProductById(int id) throws DataNotFoundException;

    void updateProduct(UpdateProductForm form);

    void deleteProduct(int id);

    Product getProductByName(String name);
    boolean isExistsProduct(String name);
    public ProductImage createProductImage(
            int productId,
            ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException;
}

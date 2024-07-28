package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.dto.ProductImageDTO;
import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.exception.InvalidParamException;
import com.example.ecommercewebsite.entity.Product;
import com.example.ecommercewebsite.entity.ProductImage;
import com.example.ecommercewebsite.form.CreatingProductForm;
import com.example.ecommercewebsite.form.ProductFilterForm;
import com.example.ecommercewebsite.form.UpdateProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

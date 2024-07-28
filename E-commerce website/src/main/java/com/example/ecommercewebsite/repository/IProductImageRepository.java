package com.example.ecommercewebsite.repository;

import com.example.ecommercewebsite.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductImageRepository extends JpaRepository<ProductImage,Integer> {
    List<ProductImage> findByProductId(int productId);
}

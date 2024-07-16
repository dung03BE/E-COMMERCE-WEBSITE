package com.example.shopapp.repository;

import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductImageRepository extends JpaRepository<ProductImage,Integer> {
    List<ProductImage> findByProductId(int productId);
}

package com.example.ecommercewebsite.repository;

import com.example.ecommercewebsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {
    Product findByName(String name);

    boolean existsByName(String name);
}

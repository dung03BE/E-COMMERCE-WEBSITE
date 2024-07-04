package com.example.shopapp.repository;

import com.example.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {
    Product findByName(String name);

    boolean existsByName(String name);
}

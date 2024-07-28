package com.example.ecommercewebsite.repository;


import com.example.ecommercewebsite.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Integer> {
}

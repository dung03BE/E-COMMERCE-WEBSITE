package com.example.ecommercewebsite.repository;

import com.example.ecommercewebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {
}

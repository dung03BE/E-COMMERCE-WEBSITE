package com.example.shopapp.repository;

import com.example.shopapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}

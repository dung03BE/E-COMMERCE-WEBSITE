package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.entity.User;
import com.example.ecommercewebsite.form.CreatingUserForm;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User findById(int id);
    User createUser(CreatingUserForm form) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}

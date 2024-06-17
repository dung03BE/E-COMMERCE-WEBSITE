package com.example.shopapp.service;

import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.User;
import com.example.shopapp.form.CreatingUserForm;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User findById(int id);
    User createUser(CreatingUserForm form) throws DataNotFoundException;
}

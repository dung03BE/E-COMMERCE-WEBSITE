package com.example.shopapp.service;

import com.example.shopapp.entity.Role;
import com.example.shopapp.form.CreatingRoleForm;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();


    void createRole(CreatingRoleForm form);


    void updateRole(Role role);

    void deleteRoleById(int id);
}

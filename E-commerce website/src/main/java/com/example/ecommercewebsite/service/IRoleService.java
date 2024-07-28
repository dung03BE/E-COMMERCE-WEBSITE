package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.entity.Role;
import com.example.ecommercewebsite.form.CreatingRoleForm;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();


    void createRole(CreatingRoleForm form);


    void updateRole(Role role);

    void deleteRoleById(int id);
}

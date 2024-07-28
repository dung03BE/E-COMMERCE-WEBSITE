package com.example.ecommercewebsite.service;


import com.example.ecommercewebsite.entity.Role;
import com.example.ecommercewebsite.form.CreatingRoleForm;
import com.example.ecommercewebsite.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService{


    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void createRole(CreatingRoleForm form) {
        Role roleNew = modelMapper.map(form,Role.class);
        roleRepository.save(roleNew);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(int id) {
        roleRepository.deleteById(id);
    }


}

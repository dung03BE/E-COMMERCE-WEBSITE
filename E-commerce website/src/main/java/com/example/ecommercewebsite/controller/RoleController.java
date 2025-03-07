package com.example.ecommercewebsite.controller;

import com.example.ecommercewebsite.dto.RoleDTO;
import com.example.ecommercewebsite.entity.Role;
import com.example.ecommercewebsite.form.CreatingRoleForm;
import com.example.ecommercewebsite.service.IRoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping
    public List<RoleDTO> getAllRoles()
    {
        List<Role> roles = roleService.getAllRoles();
        return modelMapper.map(roles,new TypeToken<List<RoleDTO>>(){}.getType());
    }
    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody CreatingRoleForm form)
    {
        roleService.createRole(form);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody Role role)
    {
        role.setId(id);
        roleService.updateRole(role);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable int id)
    {
        roleService.deleteRoleById(id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
}

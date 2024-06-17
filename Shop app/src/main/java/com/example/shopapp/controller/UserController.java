package com.example.shopapp.controller;

import com.example.shopapp.DTO.LoginUserDTO;
import com.example.shopapp.DTO.UserDTO;
import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.User;
import com.example.shopapp.form.CreatingUserForm;
import com.example.shopapp.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        List<User> users = userService.getAllUsers();
        return modelMapper.map(users,new TypeToken<List<UserDTO>>(){}.getType());
    }
    @PostMapping
    public ResponseEntity<String>  registerAccount(@RequestBody CreatingUserForm form) throws DataNotFoundException {
        User userNew = userService.createUser(form);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public String loginAccount(@RequestBody LoginUserDTO loginUserDTO)
    {
        return null;
    }
}

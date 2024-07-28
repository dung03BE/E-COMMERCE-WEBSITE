package com.example.ecommercewebsite.controller;

import com.example.ecommercewebsite.dto.LoginUserDTO;
import com.example.ecommercewebsite.dto.UserDTO;
import com.example.ecommercewebsite.entity.User;
import com.example.ecommercewebsite.form.CreatingUserForm;
import com.example.ecommercewebsite.service.IUserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        logger.info("Thông tin khách hàng");
        List<User> users = userService.getAllUsers();
        return modelMapper.map(users,new TypeToken<List<UserDTO>>(){}.getType());
    }
    @PostMapping("/register")
    public ResponseEntity<?>  registerAccount(@Valid @RequestBody CreatingUserForm form) throws Exception {
        try
        {
            User userNew = userService.createUser(form);
            return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
        }
       catch(Exception e)
       {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    @PostMapping("/login")
    public  ResponseEntity<String> loginAccount(@Valid @RequestBody LoginUserDTO loginUserDTO)  {

        try {
            String token = userService.login(loginUserDTO.getPhoneNumber(),loginUserDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }
}

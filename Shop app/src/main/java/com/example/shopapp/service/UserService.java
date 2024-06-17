package com.example.shopapp.service;

import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.Role;
import com.example.shopapp.entity.User;
import com.example.shopapp.form.CreatingUserForm;
import com.example.shopapp.repository.IRoleRepository;
import com.example.shopapp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User createUser(CreatingUserForm form) throws DataNotFoundException {

        //check xem Role tồn tại k
        Optional<Role> existingRole = Optional.of(roleRepository.getReferenceById(form.getRoleId()));
        if(!existingRole.isPresent())
        {
            throw new DataNotFoundException("Don't find roleId="+form.getRoleId());
        }
        Role role = existingRole.get();
        User user = new User();
        String phoneNumber = form.getPhoneNumber();
        //ktra so dt trùng chưa
        if(userRepository.existsByPhoneNumber(phoneNumber))
        {
            throw  new DataIntegrityViolationException("Phone number already exists");
        }
        user.setFullName(form.getFullName());
        user.setAddress(form.getAddress());
        user.setDateOfBirth(form.getDateOfBirth());
        user.setFacebookAccountId(form.getFacebookAccountId());
        user.setGoogleAccountId(form.getGoogleAccountId());
        user.setPassword(form.getPassword());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setRole(role);
        //kiem tra neu co accountId, k yc password
        if(form.getFacebookAccountId()==0 && form.getGoogleAccountId()==0)
        {
            String password = form.getPassword();
            //String encodePassword = passwordEncoder.encode(password);
            // newUser.setPassword(encodePassword)

        }
        return userRepository.save(user);
    }

}

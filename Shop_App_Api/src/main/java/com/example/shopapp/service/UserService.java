package com.example.shopapp.service;

import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.Exception.PermissionDenyException;
import com.example.shopapp.Utils.JwtTokenUtil;
import com.example.shopapp.entity.Role;
import com.example.shopapp.entity.User;
import com.example.shopapp.form.CreatingUserForm;
import com.example.shopapp.repository.IRoleRepository;
import com.example.shopapp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User createUser(CreatingUserForm form) throws Exception {

        String phoneNumber = form.getPhoneNumber();
        //ktra so dt trùng chưa
        if(userRepository.existsByPhoneNumber(phoneNumber))
        {
            throw  new DataIntegrityViolationException("Phone number already exists");
        }
        //check xem Role tồn tại k
        Role role =roleRepository.findById(form.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("Don't find role"));
        if(role.getName().toUpperCase().equals(Role.ADMIN))
        {
            throw new PermissionDenyException("Cannot register an Account with role ADMIN");
        }
        User user = new User();
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
            String encodedPassword= passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser= userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty())
        {
            throw new DataNotFoundException("Invalid phonenumber/password");
        }
        User existingUser = optionalUser.get();
        //check password
        if(existingUser.getFacebookAccountId()==0 && existingUser.getGoogleAccountId()==0)
        {
            if(!passwordEncoder.matches(password,existingUser.getPassword()))
            {
                throw new BadCredentialsException("Wrong phone number or password!");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          phoneNumber,password, existingUser.getAuthorities()
        );
        //authenticate with java swing
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


}

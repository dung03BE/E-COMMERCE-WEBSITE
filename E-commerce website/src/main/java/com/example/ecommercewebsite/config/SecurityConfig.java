package com.example.ecommercewebsite.config;

import com.example.ecommercewebsite.repository.IUserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private   IUserRepository  userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Cannot find user with phone number = "+phoneNumber));
    }
    //b2 thuc thi trong userdetailt của javasecurity trong user

    //doi tuong ma hoa pass
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //da thuc thi cac method thuat toan ma hoa
    }
    //tu userdetail và pass tạo ra đc authenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    //khoi tạo authenticationmanager
   @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

}

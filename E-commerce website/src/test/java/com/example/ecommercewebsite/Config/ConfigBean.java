package com.example.ecommercewebsite.Config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean  {
    @Bean
    public ModelMapper init()
    {
        return new ModelMapper();
    }
}

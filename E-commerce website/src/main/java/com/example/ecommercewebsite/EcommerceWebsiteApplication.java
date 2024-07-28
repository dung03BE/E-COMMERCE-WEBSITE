package com.example.ecommercewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcommerceWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceWebsiteApplication.class, args);
    }

}

package com.example.shopapp.config;

import com.example.shopapp.filter.JwtTokenFilter;
import com.example.shopapp.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(requests-> {
                     requests
                             .requestMatchers(
                                     "api/v1/users/register",
                                     "api/v1/users/login"
                              )
                             .permitAll()
                             .requestMatchers(HttpMethod.GET,"api/v1/categories/**").hasAnyRole(Role.USER,Role.ADMIN)
                             .requestMatchers(HttpMethod.POST,"api/v1/categories/**").hasAnyRole(Role.ADMIN)
                             .requestMatchers(HttpMethod.PUT,"api/v1/categories/**").hasRole(Role.ADMIN)
                             .requestMatchers(HttpMethod.DELETE,"api/v1/categories/**").hasRole(Role.ADMIN)

                             .requestMatchers(HttpMethod.GET,"api/v1/products/**").hasAnyRole(Role.USER,Role.ADMIN)
                             .requestMatchers(HttpMethod.POST,"api/v1/products/**").hasAnyRole(Role.ADMIN)
                             .requestMatchers(HttpMethod.PUT,"api/v1/products/**").hasRole(Role.ADMIN)
                             .requestMatchers(HttpMethod.DELETE,"api/v1/products/**").hasRole(Role.ADMIN)

                             .requestMatchers(HttpMethod.POST,"api/v1/orders/**").hasAnyRole(Role.USER)
                             .requestMatchers(HttpMethod.GET,"api/v1/orders/**").hasAnyRole(Role.ADMIN,Role.USER)
                             .requestMatchers(HttpMethod.PUT,"api/v1/orders/**").hasRole(Role.ADMIN)
                             .requestMatchers(HttpMethod.DELETE,"api/v1/orders/**").hasRole(Role.ADMIN)
                                .anyRequest().authenticated();
                 })
         ;
         return http.build();
    }
}

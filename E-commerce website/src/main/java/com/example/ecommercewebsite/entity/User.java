package com.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="fullname",length = 100)
    private String fullName;
    @Column(name="phone_number",length = 50)
    private String phoneNumber;
    @Column(name="address",length = 100)
    private String address;
    @Column(name="`password`",length = 100,nullable = false)
    private String password;
    @Column(name="is_active")
    private boolean active;
    @Column(name="date_of_birth")
    private Date dateOfBirth;
    @Column(name="facebook_account_id")
    private int facebookAccountId;
    @Column(name="google_account_id")
    private int googleAccountId;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
    //b2
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
     // authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber; // the hien truong duy nhất de dang nhap
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

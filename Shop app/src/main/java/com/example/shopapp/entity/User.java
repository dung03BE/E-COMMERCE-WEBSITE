package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{
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
}

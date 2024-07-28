package com.example.ecommercewebsite.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="`name`",length = 100, nullable = false)
    private String name;
    public static String ADMIN="ADMIN";
    public static String USER="USER";
}

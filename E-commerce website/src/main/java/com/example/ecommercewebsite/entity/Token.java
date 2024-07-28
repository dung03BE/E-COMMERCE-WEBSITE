package com.example.ecommercewebsite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="tokens")
@Getter
@Setter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 255,nullable = false,unique = true)
    private String token;
    @Column(length = 50,nullable = false)
    private String tokenType;
    private LocalDateTime expirationDate;
    private boolean revoked ;
    private boolean expired ;
    @ManyToOne
    @JoinColumn(name="user_id" , referencedColumnName = "id")
    private User user;
}

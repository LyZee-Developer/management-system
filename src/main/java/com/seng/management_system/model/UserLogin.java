package com.seng.management_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_login",indexes={
    @Index(name="idx_username", columnList="username"),
    @Index(name="idx_email", columnList="password"),
})
@Setter
@Getter
public class UserLogin extends IsActivateEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique = true)
    private String username;
    @Column(nullable=false)
    private String password;
    private int attempt;
    private LocalDateTime signInDate;
}

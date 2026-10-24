package com.velora.api.entity;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String name;
 @Column(nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.CUSTOMER;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;} public Role getRole(){return role;} public void setRole(Role v){role=v;} public Instant getCreatedAt(){return createdAt;}
}

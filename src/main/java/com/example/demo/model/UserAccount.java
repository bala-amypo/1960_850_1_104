// package com.example.demo.model;
// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// @Entity
// @Table(name = "user_accounts", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class UserAccount {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @Column(nullable = false)
//     private String fullName;
//     @Column(nullable = false, unique = true)
//     private String email;
//     @Column(nullable = false)
//     private String password;
//     @Column(nullable = false)
//     private String role;
//     @Column(nullable = false)
//     private Boolean active;
//     @PrePersist
//     protected void onCreate() { if (active == null) active = true; }
// }

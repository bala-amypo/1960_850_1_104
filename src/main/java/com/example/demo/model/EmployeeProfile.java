package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "employee_profiles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "employee_id"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String employeeId;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String jobRole;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        if (active == null) active = true;
        if (jobRole == null) jobRole = "STAFF";
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}

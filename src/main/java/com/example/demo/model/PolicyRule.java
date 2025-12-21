package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "policy_rules", uniqueConstraints = {@UniqueConstraint(columnNames = "rule_code")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String ruleCode;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "applies_to_role")
    private String appliesToRole;
    @Column(name = "applies_to_department")
    private String appliesToDepartment;
    @Column(nullable = false)
    private Integer maxDevicesAllowed;
    @Column(nullable = false)
    private Boolean active;
    @PrePersist
    protected void onCreate() { if (active == null) active = true; }
}

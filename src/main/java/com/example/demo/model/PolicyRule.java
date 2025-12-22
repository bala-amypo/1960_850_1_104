package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "policy_rules", uniqueConstraints = {
        @UniqueConstraint(columnNames = "rule_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleCode;

    @Column
    private String description;

    @Column
    private String appliesToRole;

    @Column
    private String appliesToDepartment;

    @Column(nullable = false)
    private Integer maxDevicesAllowed = 0;

    @Column(nullable = false)
    private Boolean active = true;
}

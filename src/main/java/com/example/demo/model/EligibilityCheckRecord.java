package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name = "eligibility_check_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityCheckRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_item_id", nullable = false)
    private DeviceCatalogItem deviceItem;
    @Column(nullable = false)
    private Boolean isEligible;
    @Column(columnDefinition = "TEXT")
    private String reason;
    @Column(nullable = false, updatable = false)
    private LocalDateTime checkedAt;
    @PrePersist
    protected void onCreate() { if (checkedAt == null) checkedAt = LocalDateTime.now(); }
}

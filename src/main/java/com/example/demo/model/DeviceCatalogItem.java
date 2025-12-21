package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "device_catalog_items", uniqueConstraints = {@UniqueConstraint(columnNames = "device_code")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String deviceCode;
    @Column(nullable = false)
    private String deviceType;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;
    @Column(nullable = false)
    private Boolean active;
    @PrePersist
    protected void onCreate() { if (active == null) active = true; }
}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "issued_device_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuedDeviceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;

    @ManyToOne
    @JoinColumn(name = "device_item_id", nullable = false)
    private DeviceCatalogItem device;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Column
    private LocalDate returnedDate;

    @Column(nullable = false)
    private String status = "ISSUED";
}

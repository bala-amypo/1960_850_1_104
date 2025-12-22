package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Profile", description = "Employee profile management endpoints")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create employee profile")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeProfile employee) {
        EmployeeProfile created = service.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(created));
    }

    @GetMapping
    @Operation(summary = "List all employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        try {
            List<EmployeeProfile> employees = service.getAllEmployees();
            return ResponseEntity.ok(employees.stream().map(this::mapToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employees: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeProfile employee = service.getEmployeeById(id);
            return ResponseEntity.ok(mapToDto(employee));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employee: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update employee active status")
    public ResponseEntity<EmployeeDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        try {
            EmployeeProfile updated = service.updateEmployeeStatus(id, active);
            return ResponseEntity.ok(mapToDto(updated));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update employee status: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            EmployeeProfile employee = service.getEmployeeById(id);
            service.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete employee: " + e.getMessage());
        }
    }

    private EmployeeDto mapToDto(EmployeeProfile employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDto(employee.getId(), employee.getEmployeeId(), 
                employee.getFullName(), employee.getEmail(), 
                employee.getDepartment(), employee.getJobRole(), employee.getActive());
    }
}

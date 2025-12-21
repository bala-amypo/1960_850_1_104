package com.example.demo.controller;

public class EmployeeProfileController {

}
package com.example.demo.controller;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management")
public class EmployeeProfileController {
    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @PostMapping
    @Operation(summary = "Create Employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeProfile employee) {
        EmployeeProfile created = employeeProfileService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(created));
    }

    @GetMapping
    @Operation(summary = "Get All Employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeProfileService.getAllEmployees().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Employee")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(employeeProfileService.getEmployeeById(id)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update Status")
    public ResponseEntity<EmployeeDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(convertToDto(employeeProfileService.updateEmployeeStatus(id, active)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeProfileService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    private EmployeeDto convertToDto(EmployeeProfile emp) {
        return new EmployeeDto(emp.getId(), emp.getEmployeeId(), emp.getFullName(), emp.getEmail(), emp.getDepartment(), emp.getJobRole(), emp.getActive());
    }
}

package com.example.demo.service;
import com.example.demo.model.EmployeeProfile;
import java.util.List;
public interface EmployeeProfileService {
    EmployeeProfile createEmployee(EmployeeProfile employee);
    EmployeeProfile getEmployeeById(Long id);
    List<EmployeeProfile> getAllEmployees();
    EmployeeProfile updateEmployeeStatus(Long id, boolean active);
    void deleteEmployee(Long id);
}

package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EligibilityCheckRecordRepository checkRepository;
    private final EmployeeProfileRepository employeeRepository;
    private final DeviceCatalogItemRepository deviceRepository;
    private final IssuedDeviceRecordRepository issuedRepository;
    private final PolicyRuleRepository policyRepository;

    public EligibilityCheckServiceImpl(EligibilityCheckRecordRepository checkRepository,
                                      EmployeeProfileRepository employeeRepository,
                                      DeviceCatalogItemRepository deviceRepository,
                                      IssuedDeviceRecordRepository issuedRepository,
                                      PolicyRuleRepository policyRepository) {
        this.checkRepository = checkRepository;
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
        this.issuedRepository = issuedRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        // Check employee exists and is active
        EmployeeProfile employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.getActive()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord();
            record.setEmployee(employee);
            record.setDevice(deviceRepository.findById(deviceItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Device not found")));
            record.setIsEligible(false);
            record.setReason("Employee is not active");
            return checkRepository.save(record);
        }

        // Check device exists and is active
        DeviceCatalogItem device = deviceRepository.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (!device.getActive()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord();
            record.setEmployee(employee);
            record.setDevice(device);
            record.setIsEligible(false);
            record.setReason("Device is inactive");
            return checkRepository.save(record);
        }

        // Check no active issuance exists
        List<IssuedDeviceRecord> active = issuedRepository.findActiveByEmployeeAndDevice(employeeId, deviceItemId);
        if (!active.isEmpty()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord();
            record.setEmployee(employee);
            record.setDevice(device);
            record.setIsEligible(false);
            record.setReason("active issuance already exists");
            return checkRepository.save(record);
        }

        // Check device max allowed per employee
        Long activeCount = issuedRepository.countActiveDevicesForEmployee(employeeId);
        if (activeCount >= device.getMaxAllowedPerEmployee()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord();
            record.setEmployee(employee);
            record.setDevice(device);
            record.setIsEligible(false);
            record.setReason("Maximum allowed devices for this device type reached");
            return checkRepository.save(record);
        }

        // Check all active policy rules
        List<PolicyRule> activeRules = policyRepository.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean roleMatches = rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(employee.getJobRole());
            boolean deptMatches = rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(employee.getDepartment());

            if (roleMatches && deptMatches) {
                if (rule.getMaxDevicesAllowed() == 0) {
                    EligibilityCheckRecord record = new EligibilityCheckRecord();
                    record.setEmployee(employee);
                    record.setDevice(device);
                    record.setIsEligible(false);
                    record.setReason("Policy violation: No devices allowed under rule " + rule.getRuleCode());
                    return checkRepository.save(record);
                }
                if (activeCount >= rule.getMaxDevicesAllowed()) {
                    EligibilityCheckRecord record = new EligibilityCheckRecord();
                    record.setEmployee(employee);
                    record.setDevice(device);
                    record.setIsEligible(false);
                    record.setReason("Policy violation: Maximum allowed devices exceeded");
                    return checkRepository.save(record);
                }
            }
        }

        // All checks passed - eligible
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployee(employee);
        record.setDevice(device);
        record.setIsEligible(true);
        record.setReason("Employee is eligible for device issuance");
        return checkRepository.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return checkRepository.findByEmployeeId(employeeId);
    }

    @Override
    public EligibilityCheckRecord getCheckById(Long id) {
        return checkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Eligibility check record not found"));
    }
}

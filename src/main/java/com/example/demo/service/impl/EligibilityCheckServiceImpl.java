package com.example.demo.service.impl;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.PolicyRule;
import com.example.demo.repository.EligibilityCheckRecordRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.EligibilityCheckService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {
    private final EligibilityCheckRecordRepository checkRepository;
    private final EmployeeProfileRepository employeeRepository;
    private final DeviceCatalogItemRepository deviceRepository;
    private final PolicyRuleRepository policyRepository;
    private final IssuedDeviceRecordRepository issuedRepository;

    public EligibilityCheckServiceImpl(EligibilityCheckRecordRepository checkRepository,
            EmployeeProfileRepository employeeRepository,
            DeviceCatalogItemRepository deviceRepository,
            PolicyRuleRepository policyRepository,
            IssuedDeviceRecordRepository issuedRepository) {
        this.checkRepository = checkRepository;
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
        this.policyRepository = policyRepository;
        this.issuedRepository = issuedRepository;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        EmployeeProfile employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        DeviceCatalogItem device = deviceRepository.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployee(employee);
        record.setDeviceItem(device);

        if (!employee.getActive()) {
            record.setIsEligible(false);
            record.setReason("Employee is not active");
            return checkRepository.save(record);
        }

        if (!device.getActive()) {
            record.setIsEligible(false);
            record.setReason("Device is not active");
            return checkRepository.save(record);
        }

        Long activeCount = issuedRepository.countActiveDevicesForEmployee(employeeId);
        if (activeCount >= device.getMaxAllowedPerEmployee()) {
            record.setIsEligible(false);
            record.setReason("Maximum allowed devices (" + device.getMaxAllowedPerEmployee() + ") already issued");
            return checkRepository.save(record);
        }

        List<PolicyRule> rules = policyRepository.findByActiveTrue();
        for (PolicyRule rule : rules) {
            if ((rule.getAppliesToRole() != null && rule.getAppliesToRole().equals(employee.getJobRole())) ||
                (rule.getAppliesToDepartment() != null && rule.getAppliesToDepartment().equals(employee.getDepartment()))) {
                if (activeCount >= rule.getMaxDevicesAllowed()) {
                    record.setIsEligible(false);
                    record.setReason("Policy violation: " + rule.getDescription());
                    return checkRepository.save(record);
                }
            }
        }

        record.setIsEligible(true);
        record.setReason("Employee is eligible for this device");
        return checkRepository.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return checkRepository.findByEmployeeId(employeeId);
    }
}

package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repository;
    private final EmployeeProfileRepository employeeRepository;
    private final DeviceCatalogItemRepository deviceRepository;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repository,
                                       EmployeeProfileRepository employeeRepository,
                                       DeviceCatalogItemRepository deviceRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        if (!employeeRepository.existsById(record.getEmployee().getId())) {
            throw new ResourceNotFoundException("Employee not found");
        }
        if (!deviceRepository.existsById(record.getDevice().getId())) {
            throw new ResourceNotFoundException("Device not found");
        }

        List<IssuedDeviceRecord> active = repository.findActiveByEmployeeAndDevice(
                record.getEmployee().getId(), record.getDevice().getId());
        if (!active.isEmpty()) {
            throw new BadRequestException("active issuance already exists for this employee-device combination");
        }

        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");
        return repository.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = getRecordById(recordId);
        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("Device already returned");
        }
        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");
        return repository.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public IssuedDeviceRecord getRecordById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issued device record not found with id: " + id));
    }
}

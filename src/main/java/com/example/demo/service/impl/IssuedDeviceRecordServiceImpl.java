package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repository;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        List<IssuedDeviceRecord> activeIssues = repository.findActiveByEmployeeAndDevice(
                record.getEmployee().getId(), 
                record.getDeviceItem().getId()
        );

        if (!activeIssues.isEmpty()) {
            throw new BadRequestException("Device already issued to this employee with active issuance");
        }

        record.setStatus("ISSUED");
        record.setIssuedDate(LocalDate.now());
        return repository.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = getRecordById(recordId);

        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("Device already returned");
        }

        record.setStatus("RETURNED");
        record.setReturnedDate(LocalDate.now());
        return repository.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public IssuedDeviceRecord getRecordById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));
    }

    @Override
    public List<IssuedDeviceRecord> getAllRecords() {
        return repository.findAll();
    }
}

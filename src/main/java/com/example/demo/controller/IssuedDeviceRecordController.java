package com.example.demo.controller;

import com.example.demo.dto.IssuedDeviceDto;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/issued-devices")
@Tag(name = "Issued Device Records", description = "Device issuance and return management")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Issue device to employee")
    public ResponseEntity<IssuedDeviceDto> issueDevice(@RequestBody IssuedDeviceDto deviceDto) {
        IssuedDeviceRecord created = service.issueDevice(deviceDto.getEmployeeId(), deviceDto.getDeviceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(created));
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return device from employee")
    public ResponseEntity<IssuedDeviceDto> returnDevice(@PathVariable Long id) {
        IssuedDeviceRecord updated = service.returnDevice(id);
        return ResponseEntity.ok(mapToDto(updated));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get issued devices for employee")
    public ResponseEntity<List<IssuedDeviceDto>> getIssuedDevicesByEmployee(@PathVariable Long employeeId) {
        List<IssuedDeviceRecord> records = service.getIssuedDevicesByEmployee(employeeId);
        return ResponseEntity.ok(records.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issued device record by ID")
    public ResponseEntity<IssuedDeviceDto> getRecordById(@PathVariable Long id) {
        IssuedDeviceRecord record = service.getRecordById(id);
        return ResponseEntity.ok(mapToDto(record));
    }

    private IssuedDeviceDto mapToDto(IssuedDeviceRecord record) {
        return new IssuedDeviceDto(record.getId(), record.getEmployee().getId(), 
                record.getDevice().getId(), record.getIssuedDate(), 
                record.getReturnedDate(), record.getStatus());
    }
}

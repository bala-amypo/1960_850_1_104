package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
@Tag(name = "Issued Devices", description = "Device issuance management")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;
    public IssuedDeviceRecordController(IssuedDeviceRecordService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Issue device to employee")
    public ResponseEntity<IssuedDeviceRecord> issueDevice(@RequestBody IssuedDeviceRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.issueDevice(record));
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return device from employee")
    public ResponseEntity<IssuedDeviceRecord> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(service.returnDevice(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get issued devices for employee")
    public ResponseEntity<List<IssuedDeviceRecord>> getDevicesForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getIssuedDevicesByEmployee(employeeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issued device record by ID")
    public ResponseEntity<IssuedDeviceRecord> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRecordById(id));
    }

    @GetMapping
    @Operation(summary = "Get all issued device records")
    public ResponseEntity<List<IssuedDeviceRecord>> getAllRecords() {
        return ResponseEntity.ok(service.getAllRecords());
    }
}

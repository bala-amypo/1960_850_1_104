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
@Tag(name = "Device Issuance")
public class IssuedDeviceRecordController {
    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Issue Device")
    public ResponseEntity<IssuedDeviceDto> issue(@RequestBody IssuedDeviceRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(service.issueDevice(record)));
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return Device")
    public ResponseEntity<IssuedDeviceDto> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(service.returnDevice(id)));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Employee Devices")
    public ResponseEntity<List<IssuedDeviceDto>> getEmployeeDevices(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getIssuedDevicesByEmployee(employeeId).stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Record")
    public ResponseEntity<IssuedDeviceDto> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(service.getRecordById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    private IssuedDeviceDto convertToDto(IssuedDeviceRecord record) {
        return new IssuedDeviceDto(record.getId(), record.getEmployee().getId(), record.getDeviceItem().getId(), record.getIssuedDate(), record.getReturnedDate(), record.getStatus());
    }
}

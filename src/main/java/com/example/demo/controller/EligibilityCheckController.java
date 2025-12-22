package com.example.demo.controller;

import com.example.demo.dto.EligibilityCheckDto;
import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility Check", description = "Device eligibility validation endpoints")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    @Operation(summary = "Validate device eligibility for employee")
    public ResponseEntity<EligibilityCheckDto> validateEligibility(@PathVariable Long employeeId, @PathVariable Long deviceItemId) {
        EligibilityCheckRecord record = service.validateEligibility(employeeId, deviceItemId);
        return ResponseEntity.ok(mapToDto(record));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all eligibility checks for employee")
    public ResponseEntity<List<EligibilityCheckDto>> getChecksByEmployee(@PathVariable Long employeeId) {
        List<EligibilityCheckRecord> records = service.getChecksByEmployee(employeeId);
        return ResponseEntity.ok(records.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{checkId}")
    @Operation(summary = "Get eligibility check by ID")
    public ResponseEntity<EligibilityCheckDto> getCheckById(@PathVariable Long checkId) {
        EligibilityCheckRecord record = service.getCheckById(checkId);
        return ResponseEntity.ok(mapToDto(record));
    }

    private EligibilityCheckDto mapToDto(EligibilityCheckRecord record) {
        return new EligibilityCheckDto(record.getId(), record.getEmployee().getId(), 
                record.getDevice().getId(), record.getIsEligible(), 
                record.getReason(), record.getCheckedAt());
    }
}

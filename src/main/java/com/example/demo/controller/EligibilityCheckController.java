package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility", description = "Device eligibility validation")
public class EligibilityCheckController {

    private final EligibilityCheckService service;
    public EligibilityCheckController(EligibilityCheckService service) { this.service = service; }

    @PostMapping("/validate")
    @Operation(summary = "Validate device eligibility for employee")
    public ResponseEntity<EligibilityCheckRecord> validateEligibility(
            @RequestParam Long employeeId,
            @RequestParam Long deviceItemId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.validateEligibility(employeeId, deviceItemId));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get eligibility checks for employee")
    public ResponseEntity<List<EligibilityCheckRecord>> getChecksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getChecksByEmployee(employeeId));
    }
}

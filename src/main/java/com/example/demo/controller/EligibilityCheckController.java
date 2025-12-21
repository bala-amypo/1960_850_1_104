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
@Tag(name = "Eligibility Checks")
public class EligibilityCheckController {
    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    @Operation(summary = "Validate Eligibility")
    public ResponseEntity<EligibilityCheckDto> validate(@PathVariable Long employeeId, @PathVariable Long deviceItemId) {
        return ResponseEntity.ok(convertToDto(service.validateEligibility(employeeId, deviceItemId)));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Employee Checks")
    public ResponseEntity<List<EligibilityCheckDto>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getChecksByEmployee(employeeId).stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Check")
    public ResponseEntity<EligibilityCheckDto> getCheck(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(service.getCheckById(id)));
    }

    private EligibilityCheckDto convertToDto(EligibilityCheckRecord record) {
        return new EligibilityCheckDto(record.getId(), record.getEmployee().getId(), record.getDeviceItem().getId(), record.getIsEligible(), record.getReason(), record.getCheckedAt());
    }
}

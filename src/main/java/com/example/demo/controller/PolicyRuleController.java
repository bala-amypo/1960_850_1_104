package com.example.demo.controller;
import com.example.demo.dto.PolicyRuleDto;
import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/policy-rules")
@Tag(name = "Policy Rules")
public class PolicyRuleController {
    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Rule")
    public ResponseEntity<PolicyRuleDto> create(@RequestBody PolicyRule rule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(service.createRule(rule)));
    }

    @GetMapping
    @Operation(summary = "Get All Rules")
    public ResponseEntity<List<PolicyRuleDto>> getAll() {
        return ResponseEntity.ok(service.getAllRules().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/active")
    @Operation(summary = "Get Active Rules")
    public ResponseEntity<List<PolicyRuleDto>> getActive() {
        return ResponseEntity.ok(service.getActiveRules().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update Status")
    public ResponseEntity<PolicyRuleDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(convertToDto(service.updateActiveStatus(id, active)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

    private PolicyRuleDto convertToDto(PolicyRule rule) {
        return new PolicyRuleDto(rule.getId(), rule.getRuleCode(), rule.getDescription(), rule.getAppliesToRole(), rule.getAppliesToDepartment(), rule.getMaxDevicesAllowed(), rule.getActive());
    }
}

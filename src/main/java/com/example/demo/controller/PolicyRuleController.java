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
@Tag(name = "Policy Rules", description = "Policy rule management endpoints")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create policy rule")
    public ResponseEntity<PolicyRuleDto> createRule(@RequestBody PolicyRule rule) {
        PolicyRule created = service.createRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(created));
    }

    @GetMapping
    @Operation(summary = "List all policy rules")
    public ResponseEntity<List<PolicyRuleDto>> getAllRules() {
        List<PolicyRule> rules = service.getAllRules();
        return ResponseEntity.ok(rules.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/active")
    @Operation(summary = "List active policy rules")
    public ResponseEntity<List<PolicyRuleDto>> getActiveRules() {
        List<PolicyRule> rules = service.getActiveRules();
        return ResponseEntity.ok(rules.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update policy rule active status")
    public ResponseEntity<PolicyRuleDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        PolicyRule updated = service.updateActiveStatus(id, active);
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete policy rule")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    private PolicyRuleDto mapToDto(PolicyRule rule) {
        return new PolicyRuleDto(rule.getId(), rule.getRuleCode(), 
                rule.getDescription(), rule.getAppliesToRole(), 
                rule.getAppliesToDepartment(), rule.getMaxDevicesAllowed(), rule.getActive());
    }
}

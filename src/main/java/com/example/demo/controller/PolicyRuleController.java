package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/policy-rules")
@Tag(name = "Policy Rules", description = "Equipment issuance policy management")
public class PolicyRuleController {

    private final PolicyRuleService service;
    public PolicyRuleController(PolicyRuleService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create policy rule")
    public ResponseEntity<PolicyRule> createRule(@RequestBody PolicyRule rule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRule(rule));
    }

    @GetMapping
    @Operation(summary = "Get all policy rules")
    public ResponseEntity<List<PolicyRule>> getAllRules() {
        return ResponseEntity.ok(service.getAllRules());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active policy rules")
    public ResponseEntity<List<PolicyRule>> getActiveRules() {
        return ResponseEntity.ok(service.getActiveRules());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update rule active status")
    public ResponseEntity<PolicyRule> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(service.updateActiveStatus(id, active));
    }
}

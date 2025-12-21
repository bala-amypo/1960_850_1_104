package com.example.demo.service;
import com.example.demo.model.PolicyRule;
import java.util.List;
public interface PolicyRuleService {
    PolicyRule createRule(PolicyRule rule);
    List<PolicyRule> getAllRules();
    List<PolicyRule> getActiveRules();
    PolicyRule updateActiveStatus(Long id, boolean active);
}

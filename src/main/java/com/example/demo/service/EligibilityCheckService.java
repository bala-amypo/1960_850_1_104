package com.example.demo.service;

import com.example.demo.model.EligibilityCheckRecord;
import java.util.List;

public interface EligibilityCheckService {
    EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId);
    List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId);
    EligibilityCheckRecord getCheckById(Long id);
}

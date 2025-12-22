package com.example.demo.repository;

import com.example.demo.model.EligibilityCheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibilityCheckRecordRepository extends JpaRepository<EligibilityCheckRecord, Long> {
    List<EligibilityCheckRecord> findByEmployeeId(Long employeeId);
}

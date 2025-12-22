package com.example.demo.repository;
import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {
    @Query("SELECT r FROM IssuedDeviceRecord r WHERE r.employee.id = :empId AND r.deviceItem.id = :devId AND r.status = 'ISSUED'")
    List<IssuedDeviceRecord> findActiveByEmployeeAndDevice(@Param("empId") Long empId, @Param("devId") Long devId);
    @Query("SELECT COUNT(r) FROM IssuedDeviceRecord r WHERE r.employee.id = :empId AND r.status = 'ISSUED'")
    Long countActiveDevicesForEmployee(@Param("empId") Long empId);
    List<IssuedDeviceRecord> findByEmployeeId(Long empId);
}

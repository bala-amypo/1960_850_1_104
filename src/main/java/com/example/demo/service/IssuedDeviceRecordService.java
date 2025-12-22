package com.example.demo.service;

import com.example.demo.model.IssuedDeviceRecord;
import java.util.List;

public interface IssuedDeviceRecordService {
    IssuedDeviceRecord issueDevice(Long employeeId, Long deviceId);
    IssuedDeviceRecord returnDevice(Long recordId);
    List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId);
    IssuedDeviceRecord getRecordById(Long id);
}

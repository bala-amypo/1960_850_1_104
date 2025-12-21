package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    private Long id;
    private String deviceCode;
    private String deviceType;
    private String model;
    private Integer maxAllowedPerEmployee;
    private Boolean active;
}

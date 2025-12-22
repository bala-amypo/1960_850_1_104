package com.example.demo.controller;

import com.example.demo.dto.DeviceDto;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Catalog", description = "Device catalog management endpoints")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create device catalog item")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceCatalogItem item) {
        DeviceCatalogItem created = service.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(created));
    }

    @GetMapping
    @Operation(summary = "List all devices")
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        List<DeviceCatalogItem> devices = service.getAllItems();
        return ResponseEntity.ok(devices.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id) {
        DeviceCatalogItem device = service.getItemById(id);
        return ResponseEntity.ok(mapToDto(device));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update device active status")
    public ResponseEntity<DeviceDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        DeviceCatalogItem updated = service.updateActiveStatus(id, active);
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        service.getItemById(id);
        return ResponseEntity.noContent().build();
    }

    private DeviceDto mapToDto(DeviceCatalogItem device) {
        return new DeviceDto(device.getId(), device.getDeviceCode(), 
                device.getDeviceType(), device.getModel(), 
                device.getMaxAllowedPerEmployee(), device.getActive());
    }
}

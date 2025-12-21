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
@Tag(name = "Device Catalog")
public class DeviceCatalogController {
    private final DeviceCatalogService deviceCatalogService;

    public DeviceCatalogController(DeviceCatalogService deviceCatalogService) {
        this.deviceCatalogService = deviceCatalogService;
    }

    @PostMapping
    @Operation(summary = "Create Device")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceCatalogItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(deviceCatalogService.createItem(item)));
    }

    @GetMapping
    @Operation(summary = "Get All Devices")
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceCatalogService.getAllItems().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Device")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(deviceCatalogService.getItemById(id)));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update Status")
    public ResponseEntity<DeviceDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(convertToDto(deviceCatalogService.updateActiveStatus(id, active)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Device")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceCatalogService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    private DeviceDto convertToDto(DeviceCatalogItem item) {
        return new DeviceDto(item.getId(), item.getDeviceCode(), item.getDeviceType(), item.getModel(), item.getMaxAllowedPerEmployee(), item.getActive());
    }
}

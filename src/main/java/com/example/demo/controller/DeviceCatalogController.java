package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "Device catalog management")
public class DeviceCatalogController {

    private final DeviceCatalogService service;
    public DeviceCatalogController(DeviceCatalogService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create device")
    public ResponseEntity<DeviceCatalogItem> createDevice(@RequestBody DeviceCatalogItem device) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createItem(device));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID")
    public ResponseEntity<DeviceCatalogItem> getDevice(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }

    @GetMapping
    @Operation(summary = "Get all devices")
    public ResponseEntity<List<DeviceCatalogItem>> getAllDevices() {
        return ResponseEntity.ok(service.getAllItems());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update device active status")
    public ResponseEntity<DeviceCatalogItem> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(service.updateActiveStatus(id, active));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}

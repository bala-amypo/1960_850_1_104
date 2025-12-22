package com.example.demo.service.impl;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.service.DeviceCatalogService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceCatalogServiceImpl implements DeviceCatalogService {

    private final DeviceCatalogItemRepository repository;

    public DeviceCatalogServiceImpl(DeviceCatalogItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceCatalogItem createItem(DeviceCatalogItem item) {
        if (repository.findByDeviceCode(item.getDeviceCode()).isPresent()) {
            throw new BadRequestException("Device with code " + item.getDeviceCode() + " already exists");
        }
        return repository.save(item);
    }

    @Override
    public DeviceCatalogItem getItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
    }

    @Override
    public List<DeviceCatalogItem> getAllItems() {
        return repository.findAll();
    }

    @Override
    public DeviceCatalogItem updateActiveStatus(Long id, boolean active) {
        DeviceCatalogItem item = getItemById(id);
        item.setActive(active);
        return repository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Device not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

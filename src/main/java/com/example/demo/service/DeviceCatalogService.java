package com.example.demo.service;
import com.example.demo.model.DeviceCatalogItem;
import java.util.List;
public interface DeviceCatalogService {
    DeviceCatalogItem createItem(DeviceCatalogItem item);
    DeviceCatalogItem getItemById(Long id);
    List<DeviceCatalogItem> getAllItems();
    DeviceCatalogItem updateActiveStatus(Long id, boolean active);
    void deleteItem(Long id);
}

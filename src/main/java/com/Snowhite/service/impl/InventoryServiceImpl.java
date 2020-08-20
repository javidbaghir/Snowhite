package com.Snowhite.service.impl;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.exception.NoDataFoundException;
import com.Snowhite.repository.InventoryRepository;
import com.Snowhite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAllByStatus(Status status) {

        List<Inventory> inventories = inventoryRepository.findAllByStatus(status);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        }

        return inventories;
    }

    @Override
    public Inventory addInventory(Inventory inventory) {

        return inventoryRepository.save(inventory);
    }

}

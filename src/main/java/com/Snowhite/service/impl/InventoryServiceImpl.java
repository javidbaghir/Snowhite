package com.Snowhite.service.impl;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.domain.WeightUnite;
import com.Snowhite.exception.InventoryNotFoundException;
import com.Snowhite.exception.NoDataFoundException;
import com.Snowhite.repository.InventoryRepository;
import com.Snowhite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Page<Inventory> findAllByStatus(Status status, Pageable pageable) {

        Page<Inventory> inventories = inventoryRepository.findAllByStatus(status, pageable);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        }

        return inventories;
    }

    @Override
    public Inventory addInventory(Inventory inventory) {

        System.out.println("Service add 1");


        inventory.setWeightUnite(WeightUnite.GRAM);

        System.out.println("Service add 2");


        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory findById(int id) {

        Inventory inventory = inventoryRepository.findById(id);

        if (inventory == null) {
            throw new InventoryNotFoundException(id);
        }

        return inventoryRepository.findById(id);
    }
}

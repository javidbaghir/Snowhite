package com.Snowhite.service.impl;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.domain.WeightUnite;
import com.Snowhite.exception.InventoryNotFoundException;
import com.Snowhite.exception.InventoryNumberAlreadyExistException;
import com.Snowhite.exception.NoDataFoundException;
import com.Snowhite.repository.InventoryRepository;
import com.Snowhite.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    public Page<Inventory> findAll(Pageable pageable) {

        Page<Inventory> inventories = inventoryRepository.findAll(pageable);

        if (inventories.isEmpty()) {
            throw new NoDataFoundException();
        }
        return inventories;
    }

    //    @Override
//    public Page<Inventory> findAllByStatus(Status status, Pageable pageable, String filter) {
//
//        Page<Inventory> inventories = inventoryRepository.findAllByStatus(status, pageable, "%" + filter + "%");
//
//        if (inventories.isEmpty()) {
//            throw new NoDataFoundException();
//        }
//
//        return inventories;
//    }

    @Override
    public Inventory addInventory(Inventory inventory) {

        Inventory number = inventoryRepository.findByInventoryNumber(inventory.getInventoryNumber());

        if (number != null) {
            throw new InventoryNumberAlreadyExistException();
        } else {
            return inventoryRepository.save(inventory);
        }
    }


    @Override
    public Inventory findById(int id) {

        Inventory inventory = inventoryRepository.findById(id);

        if (inventory == null) {
            throw new InventoryNotFoundException(id);
        }

        return inventory;
    }

    @Override
    public Long count() {
        return inventoryRepository.count();
    }

    @Override
    public List<Inventory> findDateRange(Date startDate, Date endDate) {
        List<Inventory> inventories = inventoryRepository.findDateRange(startDate, endDate);

        if (inventories == null) {
            throw new NoDataFoundException();
        }
        return inventories;
    }
}

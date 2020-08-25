package com.Snowhite.service;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {

    Page<Inventory> findAllByStatus(Status status, Pageable pageable);

    Inventory addInventory(Inventory inventory);

    Inventory findById(int id);
}

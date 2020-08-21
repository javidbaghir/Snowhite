package com.Snowhite.service;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> findAllByStatus(Status status);
//    List<Inventory> findAllByStatus(int status);

    Inventory addInventory(Inventory inventory);

    Inventory findById(int id);
}

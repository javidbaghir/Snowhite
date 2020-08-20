package com.Snowhite.service;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;

import java.util.List;

public interface InventoryService {

    List<Inventory> findAllByStatus(Status status);

    Inventory addInventory(Inventory inventory);
}

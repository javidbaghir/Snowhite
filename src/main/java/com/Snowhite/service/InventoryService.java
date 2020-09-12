package com.Snowhite.service;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InventoryService {

    Page<Inventory> findAllByStatus(Status status, Pageable pageable);

    Page<Inventory> findAll(Pageable pageable);

    Inventory addInventory(Inventory inventory);

    Inventory editInvenotory(Inventory inventory);

    Inventory findById(int id);

    Long count();

    Optional<Inventory> getInventoryByDate(Date startDate, Date endDate);

    Long getGainsByDate(Date startDate, Date endDate);

    Long getGeneralBudget();


}

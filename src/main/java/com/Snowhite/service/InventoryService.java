package com.Snowhite.service;

import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import com.Snowhite.projection.BudgetProjection;
import com.Snowhite.projection.GainProjection;
import com.Snowhite.projection.TotalWeight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface InventoryService {

    List<Inventory> findAll(Status status, Date startDate, Date endDate, Pageable pageable);

    Page<Inventory> findAll(Pageable pageable);

    Inventory addInventory(Inventory inventory);

    Integer updateInventory(Inventory inventory);

    Integer updateStatus(String status, long id);

    Integer saleInventory(long id, Double salePrice);

    Inventory findById(long id);

    List<Inventory> getInventoryByDate(Date startDate, Date endDate);

    List<GainProjection> getGainsByDate(Date startDate, Date endDate);

    List<BudgetProjection> getGeneralBudget();

    List<TotalWeight> getTotalWeight();


}
